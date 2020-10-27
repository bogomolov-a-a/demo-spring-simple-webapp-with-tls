 openssl genrsa -out $1/application.key $2
 openssl req -new -subj "/CN=Bogomolov Artem A./ST=St.Petersburg/C=RU/O=ZORN LEMMA/OU=develop" -config $5/openssl-code-sign.conf -reqexts codesign_cert -key $1/application.key -out $1/application.csr
 openssl x509 -req -extfile $5/openssl-code-sign.conf -extensions codesign_cert  -CAcreateserial -days $3  -CA $1/rootCA.crt -CAkey $1/rootCA.key -in $1/application.csr -out $1/application.crt 
 rm $1/application.csr 
echo $4'\n'$4 |openssl pkcs12 -export -out $1/application.p12 -inkey $1/application.key -in $1/application.crt -certfile $1/rootCA.crt -passout pass:
 rm $1/application.key
 rm $1/application.crt
