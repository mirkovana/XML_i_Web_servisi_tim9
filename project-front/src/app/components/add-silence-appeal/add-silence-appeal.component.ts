 
import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
 
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestService } from '../../service/request.service';
 
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
 
import Swal from "sweetalert2";
 

@Component({
  selector: 'app-add-silence-appeal',
  templateUrl: './add-silence-appeal.component.html',
  styleUrls: ['./add-silence-appeal.component.css']
})
export class AddSilenceAppealComponent implements OnInit {
  appeal = '';
  textArea: string;

  requestXmlFile: string;

  readFile = (e) => {
    console.log("e = ", e);
    const file = e.target.files[0];
    if (!file) {
      return;
    }
    this.broj = file.name.split(".")[0];
    const reader = new FileReader();
    reader.onload = (evt) => {
      const xmlData: string = (evt as any).target.result;
      //console.log("xmldata = ", xmlData);
      this.requestXmlFile = xmlData;
    };
    reader.readAsText(file);
    //console.log("reader = ", reader.readAsText(file));
  }

 
  nazivOrgana: string ='';
  option1: string ='nije postupio';
  option2:string  ='nije postupio u celosti';
  option3:string  ='u zakonskom roku';
  datumPodnosenja: string ='';
  broj: string;
  podaciOInformaciji: string ='';
  ime: string ='';
  prezime: string ='';
  potpis: string ='';
  grad: string ='';
  ulica: string ='';
  br: string ='';
  drugiPodaci: string ='';
  mesto: string ='';
  dana: string ='';
  checked1: boolean = false;
  checked2: boolean = false;
  checked3: boolean = false;
  myForm: FormGroup;
  submitted = false;
 
 
  constructor(private service: SilenceAppealService,
    private requestService: RequestService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private cd: ChangeDetectorRef) { }

  get f() { return this.myForm.controls; }

  ngOnInit(): void {
    this.myForm = new FormGroup({
      option1: new FormControl(),
      option2: new FormControl(),
      option3: new FormControl(),
      nazivOrgana: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,40}$/i)]),
      datumPodnosenja: new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      podaciOInformaciji: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,60}$/i)]),
      ime: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,20}$/i)]),
      prezime: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,30}$/i)]),
      potpis:  new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,50}$/i)]),
      grad: new FormControl('', [Validators.required,  Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      ulica: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      br: new FormControl('', [Validators.required,  Validators.pattern(/^[0-9a-z]{1,6}$/i)]),
      drugiPodaci: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,50}$/i)]),
      mesto: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,35}$/i)]),
      dana:  new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)])
    });
  }
 
  prvi(event) {
    if ( event.target.checked ) {
        this.checked1 = true;
   }}
   drugi(event) {
    if ( event.target.checked ) {
        this.checked2 = true;
   }}
   treci(event) {
    if ( event.target.checked ) {
        this.checked3 = true;
   }
}
 

  validate(){
    
    if(this.nazivOrgana == "" || this.datumPodnosenja == "" ||
        this.broj == "" || this.podaciOInformaciji == "" || this.ime == "" || this.prezime == "" || this.potpis == "" || this.grad == "" || this.ulica == "" || this.br == "" || this.drugiPodaci == "" || this.mesto == "" || this.dana == ""){
      Swal.fire({
        title: 'Error!',
        text: 'Sva polja su neophodna!',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }

    if(this.option1 == 'false' && this.option2 == 'false' && this.option3 == 'false'){
      Swal.fire({
        title: 'Error!',
        text: 'Razlog zalbe mora biti odabran!',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }

    var date_regex = /^(0[1-9]|1\d|2\d|3[01])\.(0[1-9]|1[0-2])\.(19|20)\d{2}\.$/;
    if (date_regex.test(this.datumPodnosenja) == false || date_regex.test(this.dana) == false) {
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

    console.log("AAAAAA" + this.checked1 + "   " + this.checked2 + "    " + this.checked3)
    if (this.myForm.invalid) {
      return;
    }

    if(this.checked1 == false){
      this.option1 = 'false';
    }
    else{
      this.option1='true';
    }
    if(this.checked2 == false){
      this.option2 = 'false';
    }
    else{
      this.option2='true';
    }
    if(this.checked3 == false){
      this.option3 = 'false';
    }
 
    else{
      this.option3='true';
    }
    console.log("EEEEEEEEEE" + this.option1 + "   " + this.option2 + "    " + this.option3)
 

    if (!this.validate()) {
      return;
    }

 
    let xmlSpec: string = `<?xml version="1.0" encoding="UTF-8"?>
    <zc:zalba_cutanje 
      xmlns="http://www.projekat.org/zalbazbogcutanja"
      xmlns:zc="http://www.projekat.org/zalbazbogcutanja"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/zalbazbogcutanja/`+ this.broj +`"
      xsi:schemaLocation="http://www.projekat.org/zalbazbogcutanja zalbazbogcutanja.xsd"
      broj="`+ this.broj +`"
      username="`+ localStorage.getItem('username') +`"
      status="sent"
      poverenikUsername="poverenikUsername"
      nazivOrgana="`+ this.myForm.controls.nazivOrgana.value +`">
      <zc:zalba_status xmlns:zc="http://www.projekat.org/zalbazbogcutanja" property="pred:status">sent</zc:zalba_status>
      <zc:zalba_broj xmlns:zc="http://www.projekat.org/zalbazbogcutanja" property="pred:broj">`+ this.broj +`</zc:zalba_broj>
      <zc:telo_zalbe> 
        <zc:naziv_organa xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:organVlasti">`+ this.myForm.controls.nazivOrgana.value +`</zc:naziv_organa>
        <zc:razlozi>
          <zc:razlog podvuceno="`+ this.option1 +`" id="1">nije postupio</zc:razlog>
          <zc:razlog podvuceno="`+ this.option2 +`" id="2">nije postupio u celosti</zc:razlog>
          <zc:razlog podvuceno="`+ this.option3 +`" id="3">u zakonskom roku</zc:razlog>
        </zc:razlozi>
        <zc:datum>`+ this.myForm.controls.datumPodnosenja.value +`</zc:datum> 
        <zc:podaci_o_zahtevu_i_informaciji>`+ this.myForm.controls.podaciOInformaciji.value +`</zc:podaci_o_zahtevu_i_informaciji>
      </zc:telo_zalbe>
      <zc:podaci_o_podnosiocu_zalbe>
        <zc:adresa>
          <zc:grad>`+ this.myForm.controls.grad.value +`</zc:grad>
          <zc:ulica>`+ this.myForm.controls.ulica.value +`</zc:ulica>
          <zc:broj>`+ this.myForm.controls.br.value +`</zc:broj>
        </zc:adresa>
        <zc:ime xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:ime">`+ this.myForm.controls.ime.value +`</zc:ime>
        <zc:prezime xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:prezime">`+ this.myForm.controls.prezime.value +`</zc:prezime>
        <zc:drugi_podaci_za_kontakt>`+ this.myForm.controls.drugiPodaci.value +`</zc:drugi_podaci_za_kontakt>
        <zc:potpis>`+ this.myForm.controls.potpis.value +`</zc:potpis>
      </zc:podaci_o_podnosiocu_zalbe>
      <zc:podaci_o_mestu_i_datumu_podnosenja_zalbe>
        <zc:mesto xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:mesto">`+ this.myForm.controls.mesto.value +`</zc:mesto>
        <zc:datum xmlns:zc ="http://www.projekat.org/zalbazbogcutanja" property="pred:datum">`+ this.myForm.controls.dana.value +`</zc:datum>
      </zc:podaci_o_mestu_i_datumu_podnosenja_zalbe>
    </zc:zalba_cutanje>`;

    
   
    if (this.requestXmlFile != undefined) {
      this.requestService.addDeniedRequest(this.requestXmlFile, () => {
        this.service.addSilenceAppeal(xmlSpec, () => {
          this.router.navigateByUrl('/silence-appeals');
        })
      })
    } else {
      console.log("requestxmlfile is undefined");
    }

    /*this.service.addSilenceAppeal(xmlSpec, ()=>{
      this.router.navigateByUrl('/home');
    })*/
    
  }
}
