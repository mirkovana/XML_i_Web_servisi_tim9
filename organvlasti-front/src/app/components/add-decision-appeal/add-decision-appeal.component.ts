import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { DecisionAppealService } from '../../service/decision-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DecisionAppealDTO } from "../../model/decision-appeal.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-decision-appeal',
  templateUrl: './add-decision-appeal.component.html',
  styleUrls: ['./add-decision-appeal.component.css']
})
export class AddDecisionAppealComponent implements OnInit {
  appealSpec = '';
  textArea: string;

  ime: string;
  prezime: string;
  grad: string;
  ulica: string;
  br: string;
  nazivOrgana: string;
  broj: string;
  odDatum: string;
  uputioDatum: string;
  zbogCega: string;
  mesto: string;
  datum: string;
  drugiPodaci: string;
  potpis: string;

  constructor(private service: DecisionAppealService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("sendFile");

    let xmlString: string = `<?xml version="1.0" encoding="UTF-8"?>
    <zo:zalba_na_odluku 
      xmlns="http://www.projekat.org/zalbanaodluku"
      xmlns:zo="http://www.projekat.org/zalbanaodluku"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/zalbanaodluku/`+this.broj+`"
      xsi:schemaLocation="http://www.projekat.org/zalbanaodluku zalbanaodluku.xsd"
      username="`+localStorage.getItem('username')+`"
      datum="`+this.datum+`"
      broj="`+this.broj+`">  
      <zo:podaci_o_zalbi>
        <zo:podnosilac_zalbe>
          <zo:adresa>
            <zo:grad>`+this.grad+`</zo:grad>
            <zo:ulica>`+this.ulica+`</zo:ulica>
            <zo:broj>`+this.br+`</zo:broj>
          </zo:adresa>
          <zo:ime>`+this.ime+`</zo:ime>
          <zo:prezime>`+this.prezime+`</zo:prezime>
          <zo:potpis>`+this.potpis+`</zo:potpis>
        </zo:podnosilac_zalbe>
        <zo:organ_koji_je_doneo_odluku xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:organVlasti">`+this.nazivOrgana+`</zo:organ_koji_je_doneo_odluku>
        <zo:broj_zalbe xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:broj">`+this.broj+`</zo:broj_zalbe>
        <zo:datum>`+this.odDatum+`</zo:datum>
      </zo:podaci_o_zalbi>
      <zo:sadrzaj_zalbe>
        <zo:datum>`+this.uputioDatum+`</zo:datum> 
        <zo:zbog_cega_se_pobija_odluka>`+this.zbogCega+`</zo:zbog_cega_se_pobija_odluka> 
      </zo:sadrzaj_zalbe>
      <zo:podaci_o_podnosiocu_zalbe>
        <zo:adresa>
          <zo:grad>`+this.grad+`</zo:grad>
          <zo:ulica>`+this.ulica+`</zo:ulica>
          <zo:broj>`+this.br+`</zo:broj>
        </zo:adresa>
        <zo:ime xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:ime">`+this.ime+`</zo:ime>
        <zo:prezime xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:prezime">`+this.prezime+`</zo:prezime>
        <zo:potpis>`+this.potpis+`</zo:potpis>
        <zo:drugi_podaci_za_kontakt>`+this.drugiPodaci+`</zo:drugi_podaci_za_kontakt>
      </zo:podaci_o_podnosiocu_zalbe>
      <zo:podaci_o_mestu_i_datumu_podnosenja_zalbe>
        <zo:mesto xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:mesto">`+this.mesto+`</zo:mesto>
        <zo:datum xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:datum">`+this.datum+`</zo:datum>
      </zo:podaci_o_mestu_i_datumu_podnosenja_zalbe>
    </zo:zalba_na_odluku>`;
    
    console.log(xmlString);

    /*let obj: DecisionAppealDTO = {
      "text" : xmlString
    };
    console.log(obj);*/
    this.service.addDecisionAppeal(xmlString, ()=>{
      this.router.navigateByUrl('/home');
    })
    /*.subscribe(
      response => {
        console.log("added appeal");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );*/
  }

}
