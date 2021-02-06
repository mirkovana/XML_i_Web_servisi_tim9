# XML_i_Web_servisi_tim9
Pokrenuti:
project port=8070 (portal poverenika) -> Run As -> Spring Boot App

projectsoap port-8071 (soap portala poverenika) -> Run As -> Spring Boot App

project-front port-4201 (front portala poverenika) -> ng serve

organvlasti port=8080 -> Run As -> Spring Boot App

organvlastisoap port=8050 -> Run As -> Spring Boot App

organvlasti-front port-4200 -> ng serve

na http://localhost:8081/fuseki/manage.html kreirati dva dataseta -> Project (za poverenika) i OrganVlasti (za organa vlasti)

inizijalizovati korisnike za portal poverenika ->  http://localhost:8070/api/db/init -> (korisnici -> username: [user, user2, poverenik], password: [123456789])

inizijalizovati korisnike za portal organa vlasti ->  http://localhost:8080/api/db/init -> (korisnici -> username: [user, user2, sluzbenik], password: [123456789])

pregled email poruka na https://maildrop.cc/

### 1.Kreiranje zahteva na portalu organa vlasti, pregled html-a i pdf-a podnetog zahteva
[![](http://img.youtube.com/vi/xO_NMrr7C9E/0.jpg)](http://www.youtube.com/watch?v=xO_NMrr7C9E "v1")
### 2.Kreiranje odgovora (dokument obavestenje) na zahtev gradjanina, pregled html-a i pdf-a obavestenja
[![](http://img.youtube.com/vi/7kHdatlLd0A/0.jpg)](http://www.youtube.com/watch?v=7kHdatlLd0A "v2")
### 3.Pregled dobijenog obavestenja od strane gradjanina na portalu i email-u
[![](http://img.youtube.com/vi/SAT5ZYAfLck/0.jpg)](http://www.youtube.com/watch?v=SAT5ZYAfLck "v3")
### 4.Kreiranje zalbe na odluku (postupak za zalbu na cutanje je slican i nije prikazan) na portalu poverenika, pregled html-a i pdf-a podnetog zahteva
[![](http://img.youtube.com/vi/JvS36Crm7tA/0.jpg)](http://www.youtube.com/watch?v=JvS36Crm7tA "v4")
### 5.Slanje zahteva za izjasnjenje organu vlasti od strane poverenika, pregled dokumenata vezanih za datu zalbu
[![](http://img.youtube.com/vi/ZtxscgvMDTg/0.jpg)](http://www.youtube.com/watch?v=ZtxscgvMDTg "v5")
### 6.Izjasnjenje organa vlasti na zalbu, pregled vezanih dokumenata za datu zalbu
[![](http://img.youtube.com/vi/eXQYxrgbWuQ/0.jpg)](http://www.youtube.com/watch?v=eXQYxrgbWuQ "v6")
### 7.Kreiranje dokumenta resenje od strane poverenika na dobijeno izjasnjenje organa vlasti povodom zalbe, pregled html-a i pdf-a donetog resenja
[![](http://img.youtube.com/vi/xc_mJnVWdro/0.jpg)](http://www.youtube.com/watch?v=xc_mJnVWdro "v7")

na grani A_final je rdf/json file download
### 8.Kreiranje dokumenta obavestenje od strane organa vlasti na dostavljeno resenje poverenika, pregled html-a i pdf-a obavestenja, pregled resenja poverenika i dobijenog obavestenja od strane gradjanina
[![](http://img.youtube.com/vi/kEZwSvv-CfA/0.jpg)](http://www.youtube.com/watch?v=kEZwSvv-CfA "v8")

