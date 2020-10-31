echo 'start generating TEST certificates! DON''T USE THESE CERTIFICATE IN PRODUCTION ENVIRONMENT!!!!'
if [ -z "$JAVA_HOME" ]; then
  echo 'generating impossible, because variable JAVA_HOME not set. Please set it, and try again!'
  exit 1
fi
cd ./certificate
rm -rf build
mkdir build
./ca-cert-build.sh build 4096 10000 localhost ./conf trust-storage rootCA
./localhost-cert-build.sh build 4096 500 localhost ./conf server-key
#./application-cert-build.sh build 4096 500 localhost ./conf
rm build/rootCA.key
rm build/rootCA.crt
echo 'TEST certificates build!'
