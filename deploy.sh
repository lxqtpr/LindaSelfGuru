rm build/libs/lindaSelfGuru-0.0.1-SNAPSHOT.jar
./gradlew bootJar
scp ./build/libs/lindaSelfGuru-0.0.1-SNAPSHOT.jar root@62.113.100.108:backend/LindaSelfGuru/build/libs/lindaSelfGuru-0.0.1-SNAPSHOT.jar
ssh root@62.113.100.108 -t 'cd backend/LindaSelfGuru/ && docker-compose up -d --build; bash -l'