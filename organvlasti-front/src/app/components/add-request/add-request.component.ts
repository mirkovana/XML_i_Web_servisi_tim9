import { Component, OnInit, Input } from '@angular/core';
import { RequestService } from '../../service/request.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from "sweetalert2";

declare const Xonomy: any;
@Component({
  selector: 'app-add-request',
  templateUrl: './add-request.component.html',
  styleUrls: ['./add-request.component.css']
})
export class AddRequestComponent implements OnInit {
  nazivOrgana: string = "";
  sedisteOrgana: string = "";
  option1: string;
  option2: string;
  option3: string;
  option4: string;
  option5: string;
  option6: string;
  option7: string;
  option8: string;
  drugiNacin: string;
  informacije: string = "";
  mesto: string = "";
  datum: string = "";
  ime: string = "";
  prezime: string = "";
  adresa: string = "";
  drugiPodaci: string = "";  
  potpis: string = "";

  constructor(private requestService: RequestService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }
  
  validate(){
    if(this.nazivOrgana == "" || this.sedisteOrgana == "" || this.drugiNacin == "" || this.informacije == "" ||
        this.mesto == "" || this.datum == "" || this.ime == "" || this.prezime == "" || this.adresa == "" || this.drugiPodaci == "" || this.potpis == ""){
      Swal.fire({
        title: 'Error!',
        text: 'Sva polja su neophodna!',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }
    var date_regex = /^(0[1-9]|1\d|2\d|3[01])\.(0[1-9]|1[0-2])\.(19|20)\d{2}\.$/;
    if(date_regex.test(this.datum) == false){
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format datuma => dd.mm.yyyy.',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }
    return true;
  }

  sendFile() {
    console.log("sendFile");
    console.log(this.nazivOrgana + " " +
      this.sedisteOrgana + " "+
      this.option1 + " "+
      this.option2 + " "+
      this.option3 + " "+
      this.option4 + " "+
      this.option5 + " "+
      this.option6 + " "+
      this.option7+ " "+
      this.option8+ " "+
      this.drugiNacin + " " +
      this.informacije+ " "+
      this.mesto + " "+
      this.datum+ " "+
      this.ime+ " "+
      this.prezime+ " "+
      this.adresa+ " "+
      this.drugiPodaci+ " "+  
      this.potpis);
      if(this.option1 == undefined){
        this.option1 = 'false';
      }
      if(this.option2 == undefined){
        this.option2 = 'false';
      }
      if(this.option3 == undefined){
        this.option3 = 'false';
      }
      if(this.option4 == undefined){
        this.option4 = 'false';
      }
      if(this.option5 == undefined){
        this.option5 = 'false';
      }
      if(this.option6 == undefined){
        this.option6 = 'false';
      }
      if(this.option7 == undefined){
        this.option7 = 'false';
      }
      if(this.option8 == undefined){
        this.option8 = 'false';
      }

      if (!this.validate()) {
        return;
      }

      let xmlString: string = `<?xml version="1.0" encoding="UTF-8"?>
  <za:zahtev 
      xmlns ="http://www.projekat.org/zahtev"
      xmlns:za="http://www.projekat.org/zahtev"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/zahtev/0"
      xsi:schemaLocation="http://www.projekat.org/zahtev zahtev.xsd"
      broj="0" 
      username="`+localStorage.getItem('username')+`"
      datum="`+this.datum+`"
      institucijaNaziv="`+this.nazivOrgana+`"
      time="0"
      status="sent">
      <za:zahtev_status xmlns:za ="http://www.projekat.org/zahtev" property="pred:status">sent</za:zahtev_status>
      <za:zahtev_broj xmlns:za ="http://www.projekat.org/zahtev" property="pred:broj">insert_broj_here</za:zahtev_broj>
      <za:institucija>
          <za:naziv xmlns:za ="http://www.projekat.org/zahtev" property="pred:nazivInstitucije">`+this.nazivOrgana+`</za:naziv>
          <za:mesto xmlns:za ="http://www.projekat.org/zahtev" property="pred:sedisteInstitucije">`+this.sedisteOrgana+`</za:mesto>
      </za:institucija>
      <za:tekst_zahteva>
          <za:lista>
              <za:stavka za:checked="`+this.option1+`" za:id="1">obaveštenje da li poseduje traženu informaciju;</za:stavka>
              <za:stavka za:checked="`+this.option2+`" za:id="2">uvid u dokument koji sadrži traženu informaciju;</za:stavka>
              <za:stavka za:checked="`+this.option3+`" za:id="3">kopiju dokumenta koji sadrži traženu informaciju;</za:stavka>
              <za:stavka za:checked="`+this.option4+`" za:id="4">
                  dostavljanje kopije dokumenta koji sadrži traženu informaciju:**
                  <za:lista>
                      <za:stavka za:checked="`+this.option5+`" za:id="5">поштом</za:stavka>
                      <za:stavka za:checked="`+this.option6+`" za:id="6">elektronskom postom</za:stavka>
                      <za:stavka za:checked="`+this.option7+`" za:id="7">faksom</za:stavka>
                      <za:stavka  za:checked="`+this.option8+`" za:id="8">na drugi nacin***: 
                          <za:drugi_nacin>`+this.drugiNacin+`</za:drugi_nacin>
                      </za:stavka>
                  </za:lista>
              </za:stavka>
          </za:lista>
          <za:informacije xmlns:za ="http://www.projekat.org/zahtev" property="pred:informacije">`+this.informacije+`</za:informacije>
      </za:tekst_zahteva>
      <za:mesto_datum>
          <za:mesto>`+this.mesto+`</za:mesto>
          <za:datum xmlns:za ="http://www.projekat.org/zahtev" property="pred:datum">`+this.datum+`</za:datum>
      </za:mesto_datum>
      <za:podnosilac>
          <za:osoba>
              <za:ime xmlns:za ="http://www.projekat.org/zahtev" property="pred:ime">`+this.ime+`</za:ime>
              <za:prezime xmlns:za ="http://www.projekat.org/zahtev" property="pred:prezime">`+this.prezime+`</za:prezime>
          </za:osoba>
          <za:adresa>`+this.adresa+`</za:adresa>
          <za:drugi_podaci>`+this.drugiPodaci+`</za:drugi_podaci>
          <za:potpis>`+this.potpis+`</za:potpis>
      </za:podnosilac>
  </za:zahtev>`;
  
    console.log(xmlString);

    this.requestService.addRequest(xmlString, ()=>{
      this.router.navigateByUrl('/requests');
      
    });
  }
}
