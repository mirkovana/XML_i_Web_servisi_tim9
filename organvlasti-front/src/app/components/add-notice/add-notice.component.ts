import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { NoticeService } from '../../service/notice.service';
import { Router, ActivatedRoute } from '@angular/router';
import { NoticeDTO } from "../../model/notice.model";
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
// import { forbiddenNameValidator } from 'src/app/directives/forbidden-name.directive';
import { subscribeOn } from 'rxjs/operators';

declare const Xonomy: any;
@Component({
  selector: 'app-add-notice',
  templateUrl: './add-notice.component.html',
  styleUrls: ['./add-notice.component.css']
})
export class AddNoticeComponent implements OnInit {
  appeal = '';
  textArea: string;
  podnosiocUsername: string;

  nazivOrgana: string = '';
  sedisteOrgana: string = '';
  broj: string = '';
  datum: string = '';
  ime: string = '';
  prezime: string = '';
  ulica: string = '';
  //br: string;
  grad: string = '';
  //postanskiBroj: string;
  odDatum: string = '';
  opisInformacije: string = '';
  danaDatum: string = '';
  casova: string = '';
  odCasova: string = '';
  doCasova: string = '';
  nazivUlice: string = '';
  brojUlice: string = '';
  brKancelarije: string = '';
  iznos: string = '';
  potpisLica: string = '';
  myForm: FormGroup;
  submitted = false;



  get f() { return this.myForm.controls; }

  constructor(
    private service: NoticeService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.myForm = new FormGroup({
      sedisteOrgana: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      nazivOrgana: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      broj: new FormControl(''),
      datum: new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      ime: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,20}$/i)]),
      prezime: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z]{1,30}$/i)]),
      ulica: new FormControl('', [Validators.required, Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      grad: new FormControl('', [Validators.required,  Validators.pattern(/^[A-Z ]{1,30}$/i)]),
      odDatum: new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      opisInformacije: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9!.?,:;'" ]{1,250}$/i)]),
      danaDatum: new FormControl('', [Validators.required, Validators.pattern(/^\s*(3[01]|[12][0-9]|0?[1-9])\.(1[012]|0?[1-9])\.((?:19|20)\d{2})\.\s*$/)]),
      casova: new FormControl('', [Validators.required, Validators.pattern(/^(0?[1-9]|1[0-2]):[0-5][0-9]:[0-5][0-9]$/)]),
      odCasova: new FormControl('', [Validators.required, Validators.pattern(/^(0?[1-9]|1[0-2]):[0-5][0-9]:[0-5][0-9]$/)]),
      doCasova: new FormControl('', [Validators.required, Validators.pattern(/^(0?[1-9]|1[0-2]):[0-5][0-9]:[0-5][0-9]$/)]),
      nazivUlice: new FormControl('', [Validators.required,  Validators.pattern(/^[A-Z ]{1,40}$/i)]),
      brojUlice: new FormControl('', [Validators.required, Validators.pattern(/^[0-9a-z]{1,5}$/i)]),
      brKancelarije: new FormControl('', [Validators.required, Validators.pattern(/^[0-9a-z]{1,5}$/i)]),
      iznos: new FormControl('', [Validators.required, Validators.pattern(/^[0-9]{1,12}.[0-9]{2}$/i)]),
      potpisLica: new FormControl('', [Validators.required,  Validators.pattern(/^[A-Z ]{1,50}$/i)]),
      brUlice: new FormControl('', [Validators.required,  Validators.pattern(/^[0-9a-z]{1,6}$/i)]),
      postanskiBroj : new FormControl('', [Validators.required,  Validators.pattern(/^(11[0-9]{3}|[2-3][0-9]{4})$/i)]),
    });
    this.broj = this.route.snapshot.paramMap.get('id')
    this.podnosiocUsername = this.route.snapshot.paramMap.get('podnosiocUsername')
  }

  sendFile() {
    this.submitted = true;
    // this.cd.detectChanges();
    if (this.myForm.invalid) {
      return;
    }
    console.log("Send file");
    console.log(this.submitted)

    let xmlSpec: string = `<?xml version="1.0" encoding="UTF-8"?>
    <ob:obavestenje 
      xmlns="http://www.projekat.org/obavestenje" 
      xmlns:ob="http://www.projekat.org/obavestenje" 
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
      xmlns:pred="http://www.projekat.org/predicate/"
      about="http://www.projekat.org/obavestenje/`+ this.broj + `"
      xsi:schemaLocation="http://www.projekat.org/obavestenje Obavestenje.xsd"
      broj="`+ this.broj + `"
      username="`+ this.podnosiocUsername + `"  
      datum="`+ this.datum + `"
      organVlastiUsername="`+ localStorage.getItem('username') + `">
      <ob:opste_informacije>
        <ob:podaci_o_organu>
          <ob:naziv xmlns:ob="http://www.projekat.org/obavestenje" property="pred:nazivOrgana">`+ this.nazivOrgana + `</ob:naziv>
          <ob:sediste xmlns:ob="http://www.projekat.org/obavestenje" property="pred:sedisteOrgana">`+ this.myForm.get("sedisteOrgana") + `</ob:sediste>
        </ob:podaci_o_organu>
        <ob:broj_predmeta xmlns:ob="http://www.projekat.org/obavestenje" property="pred:brojPredmeta">`+ this.broj + `</ob:broj_predmeta>
        <ob:datum xmlns:ob="http://www.projekat.org/obavestenje" property="pred:datum">`+ this.datum + `</ob:datum>
        <ob:podaci_o_podnosiocu>
          <ob:ime xmlns:ob="http://www.projekat.org/obavestenje" property="pred:ime">`+ this.ime + `</ob:ime>
          <ob:prezime xmlns:ob="http://www.projekat.org/obavestenje" property="pred:prezime">`+ this.prezime + `</ob:prezime>
          <ob:adresa>
            <ob:naziv_ulice>`+ this.ulica + `</ob:naziv_ulice>
            <ob:grad>`+ this.grad + `</ob:grad>
          </ob:adresa>
        </ob:podaci_o_podnosiocu>
      </ob:opste_informacije>
      <ob:telo>
        <ob:godina>`+ this.odDatum + `</ob:godina>
        <ob:opis>`+ this.opisInformacije + `</ob:opis>
         <ob:datum>`+ this.danaDatum + `</ob:datum>
        <ob:vreme>`+ this.casova + `</ob:vreme>
        <ob:od>`+ this.odCasova + `</ob:od>
        <ob:do>`+ this.doCasova + `</ob:do>
        <ob:adresa_organa>
          <ob:naziv_ulice>`+ this.nazivUlice + `</ob:naziv_ulice>
          <ob:broj_ulice>`+ this.brojUlice + `</ob:broj_ulice>
          <ob:broj_kancelarije>`+ this.brKancelarije + `</ob:broj_kancelarije>
        </ob:adresa_organa>
        <ob:iznos>`+ this.iznos + `</ob:iznos>
      </ob:telo>
      <ob:potpis>`+ this.potpisLica + `</ob:potpis>
    </ob:obavestenje>`;

    console.log(xmlSpec);

    this.service.addNotice(xmlSpec, () => {
      this.router.navigateByUrl('/notices');
    })

  }

}
