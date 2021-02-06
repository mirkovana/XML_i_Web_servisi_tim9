 
import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { DecisionAppealService } from '../../service/decision-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestService } from '../../service/request.service';
 
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { subscribeOn } from 'rxjs/operators';
 
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



  ime: string ='';
  prezime: string  ='';
  grad: string  ='';
  ulica: string  ='';
  br: string ='';
  nazivOrgana: string ='';
  broj: string ;
  odDatum: string ='';
  uputioDatum: string ='';
  zbogCega: string ='';
  mesto: string ='';
  datum: string ='';
  drugiPodaci: string ='';
  potpis: string ='';


  myForm: FormGroup;
  submitted = false;
  constructor(private service: DecisionAppealService,
    private requestService: RequestService,
    private router: Router,
    private formBuilder: FormBuilder,
    private cd: ChangeDetectorRef) { }

  get f() { return this.myForm.controls; }
  ngOnInit(): void {

    this.myForm = new FormGroup({
      ime: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,20}$/i)]),
      prezime: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,30}$/i)]),
      ulica: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      grad: new FormControl('', [Validators.required,  Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      br: new FormControl('', [Validators.required,  Validators.pattern(/^[0-9a-z]{1,6}$/i)]),
      nazivOrgana: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      odDatum: new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      uputioDatum:new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      zbogCega : new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,200}$/i)]),
      mesto : new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,35}$/i)]),
      datum : new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      drugiPodaci : new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,50}$/i)]),
      potpis: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,50}$/i)])
    });


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

    this.submitted = true;
    // this.cd.detectChanges();
    if (this.myForm.invalid) {
      return;
    }
     
    // console.log("sendFile");
    // console.log("xmlrequest file = ", this.requestXmlFile);

    let xmlString: string = `<?xml version="1.0" encoding="UTF-8"?>
    <zo:zalba_na_odluku 
      xmlns="http://www.projekat.org/zalbanaodluku"
      xmlns:zo="http://www.projekat.org/zalbanaodluku"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/zalbanaodluku/`+ this.broj  + `"
      xsi:schemaLocation="http://www.projekat.org/zalbanaodluku zalbanaodluku.xsd"
      username="`+ localStorage.getItem('username') + `"
      status="sent"
      poverenikUsername="poverenikUsername"
      datum="`+ this.myForm.controls.datum.value + `"
      broj="`+ this.broj + `">  
      <zo:zalba_status xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:status">sent</zo:zalba_status>
      <zo:podaci_o_zalbi>
        <zo:podnosilac_zalbe>
          <zo:adresa>
            <zo:grad>`+ this.myForm.controls.grad.value + `</zo:grad>
            <zo:ulica>`+ this.myForm.controls.ulica.value + `</zo:ulica>
            <zo:broj>`+ this.myForm.controls.br.value + `</zo:broj>
          </zo:adresa>
          <zo:ime>`+ this.myForm.controls.ime.value + `</zo:ime>
          <zo:prezime>`+ this.myForm.controls.prezime.value + `</zo:prezime>
          <zo:potpis>`+ this.myForm.controls.potpis.value + `</zo:potpis>
        </zo:podnosilac_zalbe>
        <zo:organ_koji_je_doneo_odluku xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:organVlasti">`+ this.myForm.controls.nazivOrgana.value + `</zo:organ_koji_je_doneo_odluku>
        <zo:broj_zalbe xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:broj">`+ this.broj + `</zo:broj_zalbe>
        <zo:datum>`+ this.myForm.controls.odDatum.value + `</zo:datum>
      </zo:podaci_o_zalbi>
      <zo:sadrzaj_zalbe>
        <zo:datum>`+ this.myForm.controls.uputioDatum.value + `</zo:datum> 
        <zo:zbog_cega_se_pobija_odluka>`+ this.myForm.controls.zbogCega.value + `</zo:zbog_cega_se_pobija_odluka> 
      </zo:sadrzaj_zalbe>
      <zo:podaci_o_podnosiocu_zalbe>
        <zo:adresa>
          <zo:grad>`+ this.myForm.controls.grad.value + `</zo:grad>
          <zo:ulica>`+ this.myForm.controls.ulica.value  + `</zo:ulica>
          <zo:broj>`+ this.myForm.controls.br.value + `</zo:broj>
        </zo:adresa>
        <zo:ime xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:ime">`+ this.myForm.controls.ime.value + `</zo:ime>
        <zo:prezime xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:prezime">`+ this.myForm.controls.prezime.value + `</zo:prezime>
        <zo:potpis>`+ this.myForm.controls.potpis.value  + `</zo:potpis>
        <zo:drugi_podaci_za_kontakt>`+ this.myForm.controls.drugiPodaci.value  + `</zo:drugi_podaci_za_kontakt>
      </zo:podaci_o_podnosiocu_zalbe>
      <zo:podaci_o_mestu_i_datumu_podnosenja_zalbe>
        <zo:mesto xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:mesto">`+ this.myForm.controls.mesto.value + `</zo:mesto>
        <zo:datum xmlns:zo ="http://www.projekat.org/zalbanaodluku" property="pred:datum">`+ this.myForm.controls.datum.value  + `</zo:datum>
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
