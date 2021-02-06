import { Component, OnInit } from '@angular/core';
import { NoticeService } from '../../service/notice.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from "sweetalert2";

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

  nazivOrgana: string = "";
  sedisteOrgana: string = "";
  broj: string = "";
  datum: string = "";
  ime: string = "";
  prezime: string = "";
  ulica: string = "";
  grad: string = "";
  odDatum: string = "";
  opisInformacije: string = "";
  danaDatum: string = "";
  casova: string = "";
  odCasova: string = "";
  doCasova: string = "";
  nazivUlice: string = "";
  brojUlice: string = "";
  brKancelarije: string = "";
  iznos: string = "";
  potpisLica: string = "";

  constructor(
    private service: NoticeService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.broj = this.route.snapshot.paramMap.get('id')
    this.podnosiocUsername = this.route.snapshot.paramMap.get('podnosiocUsername')
  }

  validate(): boolean {
    if (this.nazivOrgana == "" || this.sedisteOrgana == "" || this.broj == "" || this.datum == "" || this.ime == "" || this.prezime == "" || this.ulica == "" || this.grad == "" ||
      this.odDatum == "" || this.opisInformacije == "" || this.danaDatum == "" || this.casova == "" || this.odCasova == "" || this.doCasova == "" || this.nazivUlice == "" || this.brojUlice == "" || this.brKancelarije == "" || this.iznos == "" || this.potpisLica == "") {
      Swal.fire({
        title: 'Error!',
        text: 'Sva polja su neophodna! ',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }
    var date_regex = /^(0[1-9]|1\d|2\d|3[01])\.(0[1-9]|1[0-2])\.(19|20)\d{2}\.$/;
    if (date_regex.test(this.datum) == false || date_regex.test(this.danaDatum) == false) {
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format datuma => dd.mm.yyyy.',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }

    var year_regex = /^[12][0-9]{3}$/;
    if(year_regex.test(this.odDatum) == false){
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format godine => yyyy',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }

    let time_regex = /^(?:2[0-3]|[01]?[0-9]):[0-5][0-9]:[0-5][0-9]$/;
    if(time_regex.test(this.odCasova) == false ||
      time_regex.test(this.doCasova) == false ||
      time_regex.test(this.casova) == false){
        Swal.fire({
          title: 'Error!',
          text: 'Pogresan format vremena => hh:mm:ss',
          icon: 'error',
          confirmButtonColor: '#DC143C',
          confirmButtonText: 'OK'
        });
        return false;
    }

    let iznos_regex = /^[0-9]*.[0-9]{2}$/;
    if(iznos_regex.test(this.iznos) == false){
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format iznosa => 00.00',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }

    let br_regex = /^\d+$/;
    if(br_regex.test(this.brKancelarije) == false){
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format broja kancelarije',
        icon: 'error',
        confirmButtonColor: '#DC143C',
        confirmButtonText: 'OK'
      });
      return false;
    }
    return true;
  }

  sendFile() {
    console.log("Send file");
    if (!this.validate()) {
      return;
    }

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
          <ob:sediste xmlns:ob="http://www.projekat.org/obavestenje" property="pred:sedisteOrgana">`+ this.sedisteOrgana + `</ob:sediste>
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
