openssl genrsa -out $1/localhost.key $2
openssl req -new -subj "/CN=localhost/ST=St.Petersburg/C=RU/O=ZORN LEMMA/OU=develop" -config $5/openssl-server.conf -reqexts server_cert -key $1/localhost.key -out $1/localhost.csr
openssl x509 -req -extfile $5/openssl-server.conf -extensions server_cert -CAcreateserial -days $3 -CA $1/rootCA.crt -CAkey $1/rootCA.key -in $1/localhost.csr -out $1/localhost.crt
rm $1/localhost.csr
openssl pkcs12 -export -out $1/$6.p12 -inkey $1/localhost.key -in $1/localhost.crt -certfile $1/localhost.crt -certfile $1/rootCA.crt -name $4 -password \
pass:$4 -passout pass:$4
keytool -importcert -noprompt -keystore $1/$6.p12 -storepass $4 -alias $6 -file $1/rootCA.crt
rm $1/localhost.key
rm $1/localhost.crt
