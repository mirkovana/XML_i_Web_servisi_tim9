import { Component, OnInit } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { NoticeService } from '../../service/notice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { NoticeDTO } from "../../model/notice.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-notice',
  templateUrl: './add-notice.component.html',
  styleUrls: ['./add-notice.component.css']
})
export class AddNoticeComponent implements OnInit {
  appeal = '';
  textArea: string;

  nazivOrgana: string;
  sedisteOrgana: string;
  broj: string;
  datum: string;
  ime: string;
  prezime: string;
  ulica: string;
  br: string;
  grad: string;
  postanskiBroj: string;
  odDatum: string;
  opisInformacije: string;
  danaDatum: string;
  casova: string;
  odCasova: string;
  doCasova: string;
  nazivUlice: string;
  brojUlice: string;
  brKancelarije: string;
  iznos: string;
  potpisLica: string;

  constructor(
    private service: NoticeService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("Send file");

    let xmlSpec: string = `<?xml version="1.0" encoding="UTF-8"?>
    <ob:obavestenje 
      xmlns="http://www.projekat.org/obavestenje" 
      xmlns:ob="http://www.projekat.org/obavestenje" 
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/obavestenje/`+this.broj+`"
      xsi:schemaLocation="http://www.projekat.org/obavestenje Obavestenje.xsd"
      broj="`+this.broj+`"
      username="`+localStorage.getItem('username')+`"  
      datum="`+this.datum+`"
      poverenikUsername="poverenikUsername">
      <ob:opste_informacije>
        <ob:podaci_o_organu>
          <ob:naziv xmlns:ob="http://www.projekat.org/obavestenje" property="pred:nazivOrgana">`+this.nazivOrgana+`</ob:naziv>
          <ob:sediste xmlns:ob="http://www.projekat.org/obavestenje" property="pred:sedisteOrgana">`+this.sedisteOrgana+`</ob:sediste>
        </ob:podaci_o_organu>
        <ob:broj_predmeta xmlns:ob="http://www.projekat.org/obavestenje" property="pred:brojPredmeta">`+this.broj+`</ob:broj_predmeta>
        <ob:datum xmlns:ob="http://www.projekat.org/obavestenje" property="pred:datum">`+this.datum+`</ob:datum>
        <ob:podaci_o_podnosiocu>
          <ob:ime xmlns:ob="http://www.projekat.org/obavestenje" property="pred:ime">`+this.ime+`</ob:ime>
          <ob:prezime xmlns:ob="http://www.projekat.org/obavestenje" property="pred:prezime">`+this.prezime+`</ob:prezime>
          <ob:adresa>
            <ob:naziv_ulice>`+this.ulica+`</ob:naziv_ulice>
            <ob:broj_ulice>`+this.br+`</ob:broj_ulice>
            <ob:grad>`+this.grad+`</ob:grad>
            <ob:postanski_broj>`+this.postanskiBroj+`</ob:postanski_broj>
          </ob:adresa>
        </ob:podaci_o_podnosiocu>
      </ob:opste_informacije>
      <ob:telo>
        <ob:godina>`+this.odDatum+`</ob:godina>
        <ob:opis>`+this.opisInformacije+`</ob:opis>
         <ob:datum>`+this.danaDatum+`</ob:datum>
        <ob:vreme>`+this.casova+`</ob:vreme>
        <ob:od>`+this.odCasova+`</ob:od>
        <ob:do>`+this.doCasova+`</ob:do>
        <ob:adresa_organa>
          <ob:naziv_ulice>`+this.nazivUlice+`</ob:naziv_ulice>
          <ob:broj_ulice>`+this.brojUlice+`</ob:broj_ulice>
          <ob:broj_kancelarije>`+this.brKancelarije+`</ob:broj_kancelarije>
        </ob:adresa_organa>
        <ob:iznos>`+this.iznos+`</ob:iznos>
      </ob:telo>
      <ob:potpis>`+this.potpisLica+`</ob:potpis>
    </ob:obavestenje>`;

    console.log(xmlSpec);
    /*let obj : NoticeDTO = {
      "text" : xmlSpec
    }*/

    this.service.addNotice(xmlSpec, ()=>{
      this.router.navigateByUrl('/home');
    })
    /*.subscribe(
      response =>{
        console.log("added notice");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );*/
  }

}
