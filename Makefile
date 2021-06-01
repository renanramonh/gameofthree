RUN = java -jar app.jar

build-server:
	cd server && sh gradlew build
start-server:
	cd server/build/libs && ${RUN}
build-client:
	cd client && sh gradlew build
start-client-manual:
	cd client/build/libs && ${RUN} false
start-client-automatic:
	cd client/build/libs && ${RUN} true
