import { Component, OnInit, Input } from '@angular/core';
import { DecisionAppealService } from '../../service/decision-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestService } from '../../service/request.service';
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-decision-appeal',
  templateUrl: './add-decision-appeal.component.html',
  styleUrls: ['./add-decision-appeal.component.css']
})
export class AddDecisionAppealComponent implements OnInit {
  appealSpec = '';
  textArea: string;

  requestXmlFile: string;

  readFile = (e) => {
    const file = e.target.files[0];
    if (!file) {
      return;
    }
    this.broj = file.name.split(".")[0];
    const reader = new FileReader();
    reader.onload = (evt) => {
      const xmlData: string = (evt as any).target.result;
      console.log("xmldata = ", xmlData);
      this.requestXmlFile = xmlData;
    };
    reader.readAsText(file);
    console.log("reader = ", reader.readAsText(file));
  }


  ime: string = "";
  prezime: string = "";
  grad: string = "";
  ulica: string = "";
  br: string = "";
  nazivOrgana: string = "";
  broj: string = "";
  odDatum: string = "";
  uputioDatum: string = "";
  zbogCega: string = "";
  mesto: string = "";
  datum: string = "";
  drugiPodaci: string = "";
  potpis: string = "";

  constructor(private service: DecisionAppealService,
    private requestService: RequestService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  validate() {
    if (this.ime == "" || this.prezime == "" || this.grad == "" || this.ulica == "" || this.br == "" || this.nazivOrgana == "" || this.broj == "" || this.odDatum == "" || this.uputioDatum == "" || this.zbogCega == "" ||
      this.mesto == "" || this.datum == "" || this.drugiPodaci == "" || this.potpis == "") {
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
    if (date_regex.test(this.datum) == false || date_regex.test(this.odDatum) == false || date_regex.test(this.uputioDatum) == false) {
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format datuma => dd.mm.yyyy.',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }

    let br_regex = /^\d+$/;
    if (br_regex.test(this.br) == false) {
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format broja ulice',
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
    console.log("xmlrequest file = ", this.requestXmlFile);

    if (!this.validate()) {
      return;
    }

    let xmlString: string = `<?xml version="1.0" encoding="UTF-8"?>
    <zo:zalba_na_odluku 
      xmlns="http://www.projekat.org/zalbanaodluku"
      xmlns:zo="http://www.projekat.org/zalbanaodluku"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/zalbanaodluku/`+ this.broj + `"
      xsi:schemaLocation="http://www.projekat.org/zalbanaodluku zalbanaodluku.xsd"
      username="`+ localStorage.getItem('username') + `"
      status="sent"
      poverenikUsername="poverenikUsername"
      datum="`+ this.datum + `"
      broj="`+ this.broj + `">  
      <zo:zalba_status xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:status">sent</zo:zalba_status>
      <zo:podaci_o_zalbi>
        <zo:podnosilac_zalbe>
          <zo:adresa>
            <zo:grad>`+ this.grad + `</zo:grad>
            <zo:ulica>`+ this.ulica + `</zo:ulica>
            <zo:broj>`+ this.br + `</zo:broj>
          </zo:adresa>
          <zo:ime>`+ this.ime + `</zo:ime>
          <zo:prezime>`+ this.prezime + `</zo:prezime>
          <zo:potpis>`+ this.potpis + `</zo:potpis>
        </zo:podnosilac_zalbe>
        <zo:organ_koji_je_doneo_odluku xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:organVlasti">`+ this.nazivOrgana + `</zo:organ_koji_je_doneo_odluku>
        <zo:broj_zalbe xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:broj">`+ this.broj + `</zo:broj_zalbe>
        <zo:datum>`+ this.odDatum + `</zo:datum>
      </zo:podaci_o_zalbi>
      <zo:sadrzaj_zalbe>
        <zo:datum>`+ this.uputioDatum + `</zo:datum> 
        <zo:zbog_cega_se_pobija_odluka>`+ this.zbogCega + `</zo:zbog_cega_se_pobija_odluka> 
      </zo:sadrzaj_zalbe>
      <zo:podaci_o_podnosiocu_zalbe>
        <zo:adresa>
          <zo:grad>`+ this.grad + `</zo:grad>
          <zo:ulica>`+ this.ulica + `</zo:ulica>
          <zo:broj>`+ this.br + `</zo:broj>
        </zo:adresa>
        <zo:ime xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:ime">`+ this.ime + `</zo:ime>
        <zo:prezime xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:prezime">`+ this.prezime + `</zo:prezime>
        <zo:potpis>`+ this.potpis + `</zo:potpis>
        <zo:drugi_podaci_za_kontakt>`+ this.drugiPodaci + `</zo:drugi_podaci_za_kontakt>
      </zo:podaci_o_podnosiocu_zalbe>
      <zo:podaci_o_mestu_i_datumu_podnosenja_zalbe>
        <zo:mesto xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:mesto">`+ this.mesto + `</zo:mesto>
        <zo:datum xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:datum">`+ this.datum + `</zo:datum>
      </zo:podaci_o_mestu_i_datumu_podnosenja_zalbe>
    </zo:zalba_na_odluku>`;

    console.log(xmlString);

    if (this.requestXmlFile != undefined) {
      this.requestService.addDeniedRequest(this.requestXmlFile, () => {
        this.service.addDecisionAppeal(xmlString, () => {
          this.router.navigateByUrl('/decision-appeals');
        })
      })
    } else {
      console.log("requestxmlfile is undefined");
    }

  }

}
