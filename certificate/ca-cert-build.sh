openssl genrsa -out $1/rootCA.key $2
openssl req -new -x509 -config $5/openssl-ca.conf -reqexts=v3_ca -subj "/CN=Bogomolov Artem A./ST=St.Petersburg/C=RU/O=ZORN LEMMA/OU=CA" -days $3 -key $1/rootCA.key -out $1/rootCA.crt -verbose
openssl pkcs12 -export -out $1/$6.p12 -in $1/rootCA.crt -inkey $1/rootCA.key -certfile $1/rootCA.crt -name $6 -passout pass:$4
keytool -importcert -noprompt -keystore $1/$6.p12 -storepass $4 -alias $7 -file $1/rootCA.crt
sudo keytool -delete -noprompt -trustcacerts -alias rootCA  -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
sudo keytool -import -noprompt -trustcacerts -alias rootCA -file $1/rootCA.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit