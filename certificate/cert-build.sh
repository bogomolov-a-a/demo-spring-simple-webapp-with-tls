cd ./certificate
rm -rf build
mkdir build
./ca-cert-build.sh build 4096 10000  localhost ./conf trust-storage rootCA
./localhost-cert-build.sh build 4096 500 localhost ./conf server-key
#./application-cert-build.sh build 4096 500 localhost ./conf
rm build/rootCA.key
rm build/rootCA.crt
