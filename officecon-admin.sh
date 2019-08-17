#!/bin/sh


SERVICE_NAME=officecon
SERVICE_PROCESS_NAME=$SERVICE_NAME-admin
SERVICE_SHUTDOWN_PORT=9115
SERVICE_HTTP_PORT=9110
SERVICE_HTTPS_PORT=9113
SERVICE_AJP_PORT=9119


export JAVA_HOME=/usr/local/java/jdk1.8.0_202
export CATALINA_HOME=$HOME/tomcat/apache-tomcat-9.0.17
export CATALINA_BASE=$HOME/service/$SERVICE_NAME/$SERVICE_PROCESS_NAME

export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=$CLASSPATH:.
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$CATALINA_HOME/lib

#스마일콘설정 : "-Xms2048m -Xmx4048m -XX:PermSize=2048m -XX:MaxPermSize=4048m -XX:NewSize=512m -XX:MaxNewSize=1024m"
#-XX:PermSize=512m -XX:MaxPermSize=512m 는 jdk8 이상에서 지원안함
export JAVA_OPTS="$JAVA_OPTS -server -XX:+AggressiveOpts"
export JAVA_OPTS="$JAVA_OPTS -Xms512m -Xmx512m"
export JAVA_OPTS="$JAVA_OPTS -XX:NewSize=256m -XX:MaxNewSize=256m -XX:+UseAdaptiveSizePolicy"
export JAVA_OPTS="$JAVA_OPTS -XX:+DisableExplicitGC -XX:+UseParallelGC -XX:+UseParallelOldGC -verbose:gc -Xloggc:$CATALINA_BASE/logs/gc_`date +%Y%m%d`.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC"
export JAVA_OPTS="$JAVA_OPTS -Dservice.process.name=$SERVICE_PROCESS_NAME"
export JAVA_OPTS="$JAVA_OPTS -Dservice.shutdown.port=$SERVICE_SHUTDOWN_PORT -Dservice.http.port=$SERVICE_HTTP_PORT -Dservice.https.port=$SERVICE_HTTPS_PORT -Dservice.ajp.port=$SERVICE_AJP_PORT"
export JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"


#echo [$JAVA_OPTS]


case "$1" in
	start)
		XPID=`ps -ef | grep java | grep $SERVICE_PROCESS_NAME | grep -v grep | awk '{print $2}'`

	    
		if [ "$XPID" != "" ]; then
			kill -9 $XPID
	      
	      
			echo "Current pid $XPID killed..................."
		fi

                export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=dev"
                export JAVA_OPTS="$JAVA_OPTS -javaagent:/opt/apm/scouter/agent.java/scouter.agent.jar -Dscouter.config=./scouter-admin.conf"

	    
		echo "Start Tomcat ==> [$SERVICE_PROCESS_NAME]"


		$CATALINA_HOME/bin/startup.sh
		;;
	stop)
#		XPID=`ps -ef | grep java | grep $SERVICE_PROCESS_NAME | grep -v grep | awk '{print $2}'`


		if [ "$XPID" != "" ]; then
			kill -9 $XPID
			
			
			echo "Current pid $XPID killed..................."
		fi

	    
		echo "Stop Tomcat ==> [$SERVICE_PROCESS_NAME]"
	    
	    
		$CATALINA_HOME/bin/shutdown.sh
		;;
	*)
		echo "Usage: $0 { start | stop }"
	    
	    
		exit 1
		;;
esac


exit 0









#참고사항
#* -server
#	Server HotSpot JVM을 사용한다.
#	Server HotSpot JVM은 Desktop용 Appkication을 구동하는데 유리하다.
#	성능 최적화(Optimization)에 필요한 모든 과정을 최대한으로 수행한다.
#	Application의 시작시간은 느리지만, 일정 시간이 흐르면 Client HotSpot JVM에 비해 훨씬 뛰어난 성능을 보장한다.
#	(참고) Jdk 1.5부터는 Server-Class머신인 경우에는 -server 옵션이 기본적용된다.
#	Server-Class머신이란 2장 이상의 CPU와 2G이상의 메모리를 갖춘 머신을 의미한다.
#
#
#* -Djava.awt.headless
#	비윈도우 환경에서 GUI 클래스를 사용할 수 있게 하는 옵션
#
#
#* -Xms<size>
#	Java Heap의 최초 크기(Start Size)를 지정한다.
#	Java Heap은 -Xms 옵션으로 지정한 크기로 시작하며 최대 -Xmx옵션으로 지정한 크기만큼 커진다.
#	Sun HotSpt JVM 계열에서는 최초 크기와 최대 크기를 동일하게 부여할 것을 권장한다.
#	크기의 동적인 변경에 의한 오버 헤들를 최소화하기 위해서이다.
#
#
#* -Xmx<size>
#	Java Heap의 최대 크기(Maximum Size)를 지정한다.
#	-Xms 옵션으로 지정한 크기로 시작하며 최대 -Xmx옵션으로 지정한 크기만큼 커진다.
#	Sun HotSpt JVM 계열에서는 최초 크기와 최대 크기를 동일하게 부여할 것을 권장한다.
#	크기의 동적인 변경에 의한 오버 헤들를 최소화하기 위해서이다.
#
#
#* -XX:NewSize=<Value>
#	Young Generation의 시작 크기를 지정한다.
#	Young Generation의 크기는 NewSize옵션(시작크기)과 MaxNewSize옵션(최대크기)에 의해 결정된다
#
#
#* -XX:MaxNewSize=<value>
#	Young Generation의 최대 크기를 지정한다.
#	Young Generation의 크기는 NewSize옵션(시작크기)과 MaxNewSize옵션(최대크기)에 의해 결정된다
#
#
#* -XX:PermSize=<size>
#	Permanent Generation의 최초 크기를 지정한다.
#	Permanent Generation의 최대 크기는 MaxPermSize옵션에 의해 지정된다.
#	많은 수의 Class를 로딩하는 Application은 크기의 Permanent Generation을 필요로 하며,
#	Permanent Generation의 크기가 작아서 Class를 로딩하지 못하면 Out of Memory Error가 발생한다.
#
#
#* -XX:MaxPermSize=<size>
#	Permanent Generation의 최대 크기를 지정한다.
#	Permanent Generation의 시작 크기는 PermSize옵션에 의해 지정된다.
#	많은 수의 Class를 로딩하는 Application은 PermSize와 MaxPermSize옵션을 이용해 Permanent Generation의 크기를 크게 해주는 것이 좋다.
#	Permanent Generation의 크기가 작을 경우에는 Out of Memory Error가 발생한다.
#
#
#* -XX:NewRatio=<value>
#	Young Generation과 Old Generation의 비율을 결정한다.
#	예를 들어 이 값이 2이면 Yong:Old = 1:2가 되고, Young Generation의 크기는 전체 Java Heap의 1/3이 된다. 
#
#
#* -XX:SurvivorRatio=<value>
#	Survivor Space와 Eden Space의 비율을 지정한다.
#	만일 이 값이 6이면, To Survivor Ratio:From Survivor Ratio:Eden Space = 1:1:6 이 된다.
#	즉, 하나의 Survivor Space의 크기가 Young Generation의 1/8 이 된다.
#	Survivor Space의 크기가 크면 Tenured Generation으로 옮겨가기 전의 중간 버퍼 영역이 커지는 셈이다.
#	따라서 Full GC의 빈도를 줄이는 역할을 할 수 있다. 반면 Eden Space의 크기가 줄어들므로 Mirror GC가 자주 발생하게 된다. 
# 
#
#* -XX:ReservedCodeCacheSize=<value>
#	Code Cache의 최대 사이즈의 크기(byte) 설정
# 
#
#* -XX:+DisableExplicitGC
#	System.gc 호출에 의한 Explicit GC를 비활성화한다.
#	RMI에 의한 Explicit GC나 Applicaton에서의 Explicit GC를 원천적으로 방지하고자 할 경우에 사용된다.
# 
#
#* -XX:+UseConcMarkSweepGC
#	CMS Collector를 사용할 지의 여부를 지정한다.
#	GC Pause에 의한 사용자 응답 시간 저하 현상을 줄이고자 할 경우에 사용이 권장된다. 
# 
#
#* -XX:+AggressiveOpts
#	최신 HotSpot VM 성능을 최적화
# 
#
#* -Djava.net.preferIPv4Stack
#	IPv4인식하도록 만들기 위해
# 
#
#
#
#
#
#-DjvmRoute=${JVM_ROUTE}"	#load-balancing 관련!!!
#
#
#
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+UseAdaptiveSizePolicy"	#-XX:NewSize=<Value>, -XX:MaxNewSize=<value> 옵션과 관련됨!!!
#
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+UseParallelGC"
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+UseParallelOldGC"
#
#CATALINA_OPTS=" ${CATALINA_OPTS} -verbose:gc"
#CATALINA_OPTS=" ${CATALINA_OPTS} -Xloggc:${CATALINA_BASE}/logs/gc_`date "+%Y%m%d%H"`.log"
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+PrintGCDetails"
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+PrintGCDateStamps"
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+PrintGCTimeStamps"
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+PrintHeapAtGC"
#
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
#CATALINA_OPTS=" ${CATALINA_OPTS} -XX:HeapDumpPath=${CATALINA_BASE}/logs"
#CATALINA_OPTS=" ${CATALINA_OPTS} -Djava.security.egd=file:/dev/./urandom"
