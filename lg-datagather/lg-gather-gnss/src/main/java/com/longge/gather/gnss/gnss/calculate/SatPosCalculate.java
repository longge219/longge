package com.longge.gather.gnss.gnss.calculate;
import com.longge.gather.gnss.gnss.constant.GnssConstants;
import com.longge.gather.gnss.gnss.model.SatellitePosition;
import com.longge.gather.gnss.server.model.EphemerisData;
import com.longge.gather.gnss.server.model.Observations;
/**
 * @description 卫星位置计算
 * @author jianglong
 * @create 2018-11-09
 **/
public  class SatPosCalculate {
	
	/**卫星位置计算*/
	public static SatellitePosition computePosition(Observations obs, String sigCode, EphemerisData eph, double receiverClockError){
		long unixTime;
		double obsSendTime;
		double obsPseudorange = obs.getSatByIDType(eph.getSatID(), eph.getSatType()).getPseudorange(sigCode);
		if(eph.getSatType() == 'C'){
			unixTime= obs.getRefTime().getMsec() - 14000;
			obsSendTime = (obs.getRefTime().getGpsTime()-14) - obsPseudorange/ GnssConstants.SPEED_OF_LIGHT;
		}else{
			unixTime= obs.getRefTime().getMsec();
			obsSendTime = obs.getRefTime().getGpsTime() - obsPseudorange/GnssConstants.SPEED_OF_LIGHT;
		}
		
		
		int i=0;
		double a=0,n=0,mk=0,ek1=0,ek2=0,ek=0,vk=0;
		double deltaToc=0,deltaToe=0;
		double relClockErr=0,relFreqErr=0;
		double cosek=0,sinek=0;
		double phik=0,cos2phik=0,sin2phik=0;
		double deltauk=0,deltark=0,deltaik=0,uk=0,rk=0,ik=0;
		double cosuk=0,sinuk=0;
		double xk=0,yk=0,zk=0,vxk=0,vyk=0,vzk=0;
		double Xk=0,Yk=0,Zk=0,vXk=0,vYk=0,vZk=0;
		double omegak=0;
		double cosomegak=0,sinomegak=0,cosik=0,sinik=0;
		double[][] R = new double[3][3];
		double[][]Rdot = new double[3][3];
		double[][]RzRx = new double[3][3];
		double[][]RzRxdot = new double[3][3];
		double phikdot=0,ekdot=0,ukdot=0,rkdot=0,ikdot=0,omegakdot=0;
		double alpha=0,beta=0,sinalpha=0,cosalpha=0,sinbeta=0,cosbeta=0;
		double sendTime=0;
		double mu=0;
		double omegaedot = 0;
		double trasmit = 0.0, tcorr = 0.0;
		
		//定义返回值--卫星位置
		double posX;
		double posY;
		double posZ;
		//定义返回值--卫星速度
		double velx;
		double vely;
		double velz;
		
		trasmit =obsSendTime;
		//计算卫星时钟误差。pPvtInfo->m_dblSendTime卫星发送时刻，pContent->m_dblToc直接星历时间
		deltaToc = obsSendTime - eph.getToc();
		
		if (deltaToc < -GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc  + GnssConstants.SEC_IN_WEEK;;
		}
		else if (deltaToc > GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc -  GnssConstants.SEC_IN_WEEK;
		}
		tcorr=eph.getAf0() +deltaToc*(eph.getAf1() +deltaToc*eph.getAf2());
		//卫星钟差经二次迭代，计算更为准确
		trasmit = trasmit - tcorr;
		deltaToc=trasmit-eph.getToc();
		if (deltaToc < -GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc  + GnssConstants.SEC_IN_WEEK;
		}
		else if (deltaToc > GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc - GnssConstants.SEC_IN_WEEK;
		}
		tcorr=eph.getAf0() +deltaToc*(eph.getAf1()+deltaToc*eph.getAf2());
		sendTime=obsSendTime-tcorr;
		// 计算轨道长半轴
		a=eph.getRootA()*eph.getRootA();
		if (Math.abs(a)<=1.0E-3)
		{
			return null;
		}
		//计算卫星轨道平均角速度n=n0+deltan
		if(eph.getSatType() =='C')
		{
			mu=GnssConstants.EARTH_GRAVITATIONAL_CONSTANT_BDS;
			omegaedot = GnssConstants.OMEGAE_DOT_BDS;
		}
		else if(eph.getSatType() =='G')
		{
			mu=GnssConstants.EARTH_GRAVITATIONAL_CONSTANT_GPS;
			omegaedot = GnssConstants.OMEGAE_DOT_GPS;
		}
		n=Math.sqrt(mu/(a*a*a))+eph.getDeltaN();	
		//计算与星历参考时间toe的时间差
		deltaToe=sendTime-eph.getToe();	
		if (deltaToe > GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToe = deltaToe - GnssConstants.SEC_IN_WEEK;
		}
		if(deltaToe<-GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToe = deltaToe + GnssConstants.SEC_IN_WEEK;
		}
		//计算平均近点角mk
		mk=eph.getM0()+n*deltaToe;
		//迭代计算偏近点角ek
		ek1 = mk;
		for (i = 0; i < 10; i++)							
		{
			ek2=mk+ eph.getE()*Math.sin(ek1);
			if (Math.abs(ek2-ek1)<=1e-12)
			{
				break;
			}
			ek1 = ek2;
		}
		ek = ek2;

		//计算发射时中的相对论误差
		if(eph.getSatType() == 'C')
		{
			//XX2 GEO
			relClockErr = -4.4428073090439775E-10* eph.getE()*eph.getRootA()*Math.sin(ek);
		}
		if(eph.getSatType() == 'G')
		{
			relClockErr = -4.4428076333930602E-10* eph.getE()*eph.getRootA()*Math.sin(ek);
		}
		//计算真近点角vk
		cosek=Math.cos(ek);		
		sinek = Math.sin(ek);
		vk = Math.atan2(Math.sqrt(1 - Math.pow(eph.getE(), 2)) * sinek, cosek - eph.getE());
		//计算卫星纬度phik
		phik=vk + eph.getOmega();
		cos2phik=Math.cos(2.0*phik);
		sin2phik=Math.sin(2.0*phik);
		//计算摄动改正项deltauk
		deltauk = eph.getCus()*sin2phik+eph.getCuc()*cos2phik;
		//计算摄动改正项deltark
		deltark=  eph.getCrs()*sin2phik+eph.getCrc()*cos2phik;
		//计算摄动改正项deltaik
		deltaik= eph.getCis()*sin2phik+eph.getCic()*cos2phik;
		//计算摄动轨道参数uk,rk,ik
		uk=phik+deltauk;
		rk = a*(1- eph.getE()*cosek)+deltark;
		ik=  eph.getI0()+eph.getIDot()*deltaToe + deltaik;
		//计算卫星在轨道平面中的位置(xk,yk,zk)
		cosuk=Math.cos(uk);
		sinuk=Math.sin(uk);
		xk = rk * cosuk;
		yk = rk * sinuk;
		zk = 0;
		//计算历元升交点的经度(惯性系)
		if (eph.getSatType()=='C'&&eph.getSatID()<6)
		{
			//XX2 GEO
			omegak=eph.getOmega0()+eph.getOmegaDot()*deltaToe - omegaedot*eph.getToe();
			omegakdot=eph.getOmegaDot();
		}
		else
		{
			omegak = eph.getOmega0()+(eph.getOmegaDot()-omegaedot)*deltaToe - omegaedot*eph.getToe();
			omegakdot= eph.getOmegaDot() - omegaedot;
		}
		//计算卫星的轨道坐标系到ECEF坐标系转换矩阵R[3][3](对于GEO是到惯性系)		
		cosomegak=Math.cos(omegak);
		sinomegak=Math.sin(omegak);
		cosik=Math.cos(ik);
		sinik=Math.sin(ik);
		R[0][0] = cosomegak;
		R[0][1] = -sinomegak * cosik;
		R[0][2] = 0;
		R[1][0] = sinomegak;
		R[1][1] = cosomegak * cosik;
		R[1][2] = 0;
		R[2][0] = 0;
		R[2][1] = sinik;
		R[2][2] = 0;

		//计算卫星在ECEF坐标系中位置(x,y,z)(对于GEO是到惯性系)
		Xk = R[0][0] * xk + R[0][1] * yk + R[0][2] * zk;
		Yk = R[1][0] * xk + R[1][1] * yk + R[1][2] * zk;
		Zk = R[2][0] * xk + R[2][1] * yk + R[2][2] * zk;

		//计算卫星摄动轨道参数变化率ukdot,rkdot,ikdot
		phikdot= n*Math.sqrt((1-eph.getE()*eph.getE()))/((1-eph.getE()*cosek)*(1-eph.getE()*cosek)); 
		ekdot=n/(1- eph.getE()*cosek);
		ukdot=2*phikdot*(eph.getCus()*cos2phik-eph.getCuc()*sin2phik)+phikdot;
		rkdot=2*phikdot*(eph.getCrs()*cos2phik-eph.getCrc()*sin2phik)+ekdot*a*eph.getE()*Math.sin(ek);
		ikdot=2*phikdot*(eph.getCis()*cos2phik-eph.getCic()*sin2phik)+ eph.getIDot();

		//计算卫星在轨道系中的速度(vxk,vyk,vzk)
		vxk = rkdot * cosuk - ukdot * rk * sinuk;
		vyk = rkdot * sinuk + ukdot * rk * cosuk;
		vzk = 0;

		//计算轨道坐标系到ECEF坐标系转换矩阵的导数Rdot	(对于GEO是到惯性系)
		Rdot[0][0] = -omegakdot * sinomegak;
		Rdot[0][1] = ikdot * sinomegak * sinik-omegakdot * cosomegak * cosik;
		Rdot[0][2] = 0;
		Rdot[1][0] = omegakdot * cosomegak;
		Rdot[1][1] =-ikdot * cosomegak * sinik-omegakdot * sinomegak * cosik;
		Rdot[1][2] = 0;
		Rdot[2][0] = 0;
		Rdot[2][1] = ikdot * cosik;
		Rdot[2][2] = 0;

		//计算卫星在ECEF坐标系中的速度(vx,vy,vz)(对于GEO是到惯性系)
		vXk = R[0][0] * vxk + R[0][1] * vyk + R[0][2] * vzk + Rdot[0][0] * xk + Rdot[0][1] * yk + Rdot[0][2] * zk;
		vYk = R[1][0] * vxk + R[1][1] * vyk + R[1][2] * vzk + Rdot[1][0] * xk + Rdot[1][1] * yk + Rdot[1][2] * zk;
		vZk = R[2][0] * vxk + R[2][1] * vyk + R[2][2] * vzk + Rdot[2][0] * xk + Rdot[2][1] * yk + Rdot[2][2] * zk;

		if(eph.getSatType()=='C'&&eph.getSatID()<6){
			//计算卫星在ECEF坐标系中位置
			//XX2 GEO
			alpha = omegaedot * deltaToe;
			beta=-5.0*(GnssConstants.PI_ORBIT/180.0);
			sinalpha=Math.sin(alpha);
			cosalpha=Math.cos(alpha);
			sinbeta=Math.sin(beta);
			cosbeta=Math.cos(beta);
			RzRx[0][0] = cosalpha;
			RzRx[0][1] = sinalpha * cosbeta;
			RzRx[0][2] = sinalpha * sinbeta;
			RzRx[1][0] = -sinalpha;
			RzRx[1][1] = cosalpha * cosbeta;
			RzRx[1][2] = cosalpha * sinbeta;
			RzRx[2][0] = 0;
			RzRx[2][1] = -sinbeta;
			RzRx[2][2] = cosbeta;
			posX=RzRx[0][0]*Xk+RzRx[0][1]*Yk+RzRx[0][2]*Zk;
			posY=RzRx[1][0]*Xk+RzRx[1][1]*Yk+RzRx[1][2]*Zk;
			posZ=RzRx[2][0]*Xk+RzRx[2][1]*Yk+RzRx[2][2]*Zk;

			//计算惯性坐标系到ECEF坐标系转换矩阵的导数Rdot
			RzRxdot[0][0] = -omegaedot * sinalpha;
			RzRxdot[0][1] = omegaedot * cosalpha * cosbeta;
			RzRxdot[0][2] = omegaedot * cosalpha * sinbeta;
			RzRxdot[1][0] = -omegaedot * cosalpha;
			RzRxdot[1][1] = -omegaedot * sinalpha * cosbeta;
			RzRxdot[1][2] = -omegaedot * sinalpha * sinbeta;
			RzRxdot[2][0] = 0;
			RzRxdot[2][1] = 0;
			RzRxdot[2][2] = 0;

			//将GEO卫星速度旋转到发射时刻的ECEF坐标系			
			 velx=RzRx[0][0]*vXk+RzRx[0][1]*vYk+RzRx[0][2]*vZk+RzRxdot[0][0]*Xk+RzRxdot[0][1]*Yk+RzRxdot[0][2]*Zk;
			 vely=RzRx[1][0]*vXk+RzRx[1][1]*vYk+RzRx[1][2]*vZk+RzRxdot[1][0]*Xk+RzRxdot[1][1]*Yk+RzRxdot[1][2]*Zk;
			 velz=RzRx[2][0]*vXk+RzRx[2][1]*vYk+RzRx[2][2]*vZk+RzRxdot[2][0]*Xk+RzRxdot[2][1]*Yk+RzRxdot[2][2]*Zk;
		}else{
			posX=Xk;
			posY=Yk;
			posZ=Zk;
			 velx=vXk;
			 vely=vYk;
			 velz=vZk;
		}
		// 计算发射时中的相对论误差
		if (eph.getSatType() == 'C')
		{
			//XX2 GEO
			relFreqErr=-4.4428073090439775E-10* eph.getE()*eph.getRootA()*ekdot*Math.cos(ek);	
		}
		else if(eph.getSatType() == 'G')
		{
			relFreqErr=-4.4428076333930602E-10*  eph.getE()*eph.getRootA()*ekdot*Math.cos(ek);
		}
		// 计算卫星钟差
		trasmit=obsSendTime;
		//计算与星历时钟参考时间toc的时间差
		deltaToc = obsSendTime - eph.getToc();
		if ( deltaToc < -GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc  + GnssConstants.SEC_IN_WEEK;
		}
		else if (deltaToc > GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc - GnssConstants.SEC_IN_WEEK;
		}
		//卫星钟差计算
		tcorr=  eph.getAf0()+ deltaToc*(eph.getAf1()+deltaToc*eph.getAf2());
		//二次迭代计算卫星钟差
		trasmit = trasmit - tcorr;
		deltaToc=trasmit- eph.getToc();
		if (deltaToc < -GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc =  deltaToc  + GnssConstants.SEC_IN_WEEK;
		}
		else if (deltaToc>GnssConstants.SEC_IN_HALF_WEEK)
		{
			deltaToc = deltaToc - GnssConstants.SEC_IN_WEEK;
		}
		tcorr= eph.getAf0()+deltaToc*(eph.getAf1()+deltaToc*eph.getAf2());
		//计算卫星频差
		double freqErr =  eph.getAf1()+2.0*deltaToc*eph.getAf2()+relFreqErr;
		//卫星钟差修正相对论效应
		double clkerr  = tcorr + relClockErr;

	   SatellitePosition sp = new SatellitePosition(unixTime,eph.getSatID(), eph.getSatType(), posX,posY,posZ);
	   sp.setSatelliteClockError(tcorr);
		return sp;
	}

}
