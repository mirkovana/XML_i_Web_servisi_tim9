import { Component, OnInit, Input } from '@angular/core';
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { RequestService } from '../../service/request.service';
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

  nazivOrgana: string = "";
  option1: string;
  option2: string;
  option3: string;
  datumPodnosenja: string = "";
  broj: string = "";
  podaciOInformaciji: string = "";
  ime: string = "";
  prezime: string = "";
  potpis: string = "";
  grad: string = "";
  ulica: string = "";
  br: string = "";
  drugiPodaci: string = "";
  mesto: string = "";
  dana: string = "";

  constructor(private service: SilenceAppealService,
    private requestService: RequestService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
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

    if (!this.validate()) {
      return;
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
      status="sent"
      poverenikUsername="poverenikUsername"
      nazivOrgana="`+this.nazivOrgana+`">
      <zc:zalba_status xmlns:zc="http://www.projekat.org/zalbazbogcutanja" property="pred:status">sent</zc:zalba_status>
      <zc:zalba_broj xmlns:zc="http://www.projekat.org/zalbazbogcutanja" property="pred:broj">`+this.broj+`</zc:zalba_broj>
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
