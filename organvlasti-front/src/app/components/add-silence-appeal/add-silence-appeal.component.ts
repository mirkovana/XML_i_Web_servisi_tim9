import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { SilenceAppealDTO } from "../../model/silence-appeal.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-silence-appeal',
  templateUrl: './add-silence-appeal.component.html',
  styleUrls: ['./add-silence-appeal.component.css']
})
export class AddSilenceAppealComponent implements OnInit {
  appeal = '';
  textArea: string;

  nazivOrgana: string;
  option1: string;
  option2: string;
  option3: string;
  datumPodnosenja: string;
  broj: string;
  podaciOInformaciji: string;
  ime: string;
  prezime: string;
  potpis: string;
  grad: string;
  ulica: string;
  br: string;
  drugiPodaci: string;
  mesto: string;
  dana: string;

  constructor(private service: SilenceAppealService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("sendFile");
    if(this.option1 == undefined){
      this.option1 = 'false';
    }
    if(this.option2 == undefined){
      this.option2 = 'false';
    }
    if(this.option3 == undefined){
      this.option3 = 'false';
    }

    let xmlSpec: string = `<?xml version="1.0" encoding="UTF-8"?>
    <zc:zalba_cutanje 
      xmlns="http://www.projekat.org/zalbazbogcutanja"
      xmlns:zc="http://www.projekat.org/zalbazbogcutanja"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/zalbazbogcutanja/`+this.broj+`"
      xsi:schemaLocation="http://www.projekat.org/zalbazbogcutanja zalbazbogcutanja.xsd"
      broj="`+this.broj+`"
      username="`+localStorage.getItem('username')+`"
      nazivOrgana="`+this.nazivOrgana+`">
      <zc:broj xmlns:zc="http://www.projekat.org/zalbazbogcutanja" property="pred:broj">`+this.broj+`</zc:broj>
      <zc:telo_zalbe> 
        <zc:naziv_organa xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:organVlasti">`+this.nazivOrgana+`</zc:naziv_organa>
        <zc:razlozi>
          <zc:razlog podvuceno="`+this.option1+`" id="1">nije postupio</zc:razlog>
          <zc:razlog podvuceno="`+this.option2+`" id="2">nije postupio u celosti</zc:razlog>
          <zc:razlog podvuceno="`+this.option3+`" id="3">u zakonskom roku</zc:razlog>
        </zc:razlozi>
        <zc:datum>`+this.datumPodnosenja+`</zc:datum> 
        <zc:podaci_o_zahtevu_i_informaciji>`+this.podaciOInformaciji+`</zc:podaci_o_zahtevu_i_informaciji>
      </zc:telo_zalbe>
      <zc:podaci_o_podnosiocu_zalbe>
        <zc:adresa>
          <zc:grad>`+this.grad+`</zc:grad>
          <zc:ulica>`+this.ulica+`</zc:ulica>
          <zc:broj>`+this.br+`</zc:broj>
        </zc:adresa>
        <zc:ime xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:ime">`+this.ime+`</zc:ime>
        <zc:prezime xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:prezime">`+this.prezime+`</zc:prezime>
        <zc:drugi_podaci_za_kontakt>`+this.drugiPodaci+`</zc:drugi_podaci_za_kontakt>
        <zc:potpis>`+this.potpis+`</zc:potpis>
      </zc:podaci_o_podnosiocu_zalbe>
      <zc:podaci_o_mestu_i_datumu_podnosenja_zalbe>
        <zc:mesto xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:mesto">`+this.mesto+`</zc:mesto>
        <zc:datum xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:datum">`+this.dana+`</zc:datum>
      </zc:podaci_o_mestu_i_datumu_podnosenja_zalbe>
    </zc:zalba_cutanje>`;
    console.log(xmlSpec);
    /*let obj: SilenceAppealDTO = {
      "text" : xmlSpec
    };
    console.log(obj);*/
    this.service.addSilenceAppeal(xmlSpec, ()=>{
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
