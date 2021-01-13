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
      username="username"
      datum="`+this.datum+`"
      broj="`+this.broj+`">  
      <zo:naslov>  
        ŽALBA PROTIV ODLUKE ORGANA VLASTI KOJOM JE 
        ODBIJEN ILI ODBAČEN ZAHTEV ZA PRISTUP INFORMACIJI
      </zo:naslov>
      <zo:podaci_o_povereniku>
        <zo:adresa>
          <zo:grad>Beograd</zo:grad>
          <zo:ulica>Bulevar kralja</zo:ulica>
          <zo:broj>15</zo:broj>
        </zo:adresa>
        <zo:naziv_poverenika>Povereniku za informacije od javnog značaja i zaštitu podataka o ličnosti</zo:naziv_poverenika>
      </zo:podaci_o_povereniku>
      <zo:podaci_o_zalbi>
        <zo:naslov>ZALBA</zo:naslov>
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
        protiv resenja-zakljucka
        <zo:organ_koji_je_doneo_odluku xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:organVlasti">`+this.nazivOrgana+`</zo:organ_koji_je_doneo_odluku>
        <zo:broj_zalbe xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:broj">`+this.broj+`</zo:broj_zalbe>
        <zo:datum>`+this.odDatum+`</zo:datum>
      </zo:podaci_o_zalbi>
      <zo:sadrzaj_zalbe>
        Navedenom odlukom organa vlasti (rešenjem, zaključkom, obaveštenjem u pisanoj formi sa 
        elementima odluke) , suprotno zakonu, odbijen-odbačen je moj zahtev koji sam podneo/la-uputio/la
        dana <zo:datum>`+this.uputioDatum+`</zo:datum> godine i tako mi uskraćeno-onemogućeno ostvarivanje ustavnog i zakonskog 
        prava na slobodan pristup informacijama od javnog značaja. Odluku pobijam u celosti, odnosno u 
        delu kojim
        <zo:zbog_cega_se_pobija_odluka>`+this.zbogCega+`</zo:zbog_cega_se_pobija_odluka> jer nije zasnovana na Zakonu o slobodnom pristupu informacijama od javnog značaja.
        Na osnovu iznetih razloga, predlažem da 
        <zo:predlog_povereniku>Poverenik uvaži moju žalbu, poništi odluka prvostepenog organa i omogući mi pristup traženoj/im informaciji/ma.</zo:predlog_povereniku>
        Žalbu podnosim blagovremeno, u zakonskom roku utvrđenom u 
        članu 22. st. 1. Zakona o slobodnom pristupu informacijama od javnog značaja.
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
      <zo:napomena> 
        <zo:tacka>
          U žalbi se mora navesti odluka koja se pobija (rešenje, zaključak, obaveštenje), 
          naziv organa koji je odluku doneo, kao i broj i datum odluke. 
          Dovoljno je da žalilac navede u žalbi u kom pogledu je nezadovoljan odlukom,
          s tim da žalbu ne mora posebno obrazložiti. 
          Ako žalbu izjavljuje na ovom obrascu, dodatno obrazloženje može posebno priložiti.
        </zo:tacka>
        <zo:tacka>
          Uz žalbu obavezno priložiti kopiju podnetog zahteva i dokaz o njegovoj 
          predaji-upućivanju organu kao i kopiju odluke organa koja se osporava žalbom.
        </zo:tacka>
      </zo:napomena>
    </zo:zalba_na_odluku>`;
    
    console.log(xmlString);

    let obj: DecisionAppealDTO = {
      "text" : xmlString
    };
    console.log(obj);
    this.service.addDecisionAppeal(obj).subscribe(
      response => {
        console.log("added appeal");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );
  }

}
