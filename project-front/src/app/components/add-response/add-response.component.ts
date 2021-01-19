import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { ResponseService } from '../../service/response.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ResponseDTO } from "../../model/response.model";

//declare const Xonomy: any;
@Component({
  selector: 'app-add-response',
  templateUrl: './add-response.component.html',
  styleUrls: ['./add-response.component.css']
})
export class AddResponseComponent implements OnInit {
  zalbaSpec = '';
  textArea: string;

  status: string = "";
  broj: string = "";
  datum: string = "";
  uvod: string = "";
  resenje: string = "";
  obrazlozenje: string = "";
  poverenikIme: string = "";
  poverenikPrezime: string = "";

  constructor(private responseService: ResponseService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  /*ngAfterViewInit() {
    this.zalbaSpec = '<res:zalba xmlns="http://www.projekat.org/resenje"'
                                + ' xmlns:pred="http://www.projekat.org/predicate/"'
                                + ' xmlns:res="http://www.projekat.org/resenje"></res:zalba>';
    
    let xonomyElement = document.getElementById("response");
    Xonomy.render(this.zalbaSpec, xonomyElement, XonomyModel.zalbaXonomy);
  }*/


  sendFile() {
    console.log("sendFile");

    console.log(this.status);
    console.log(this.broj);
    console.log(this.datum);
    console.log(this.uvod);
    console.log(this.resenje);
    console.log(this.obrazlozenje);
    console.log(this.poverenikIme);
    console.log(this.poverenikPrezime);

    let xmlString = `<?xml version="1.0" encoding="UTF-8"?>
    <res:zalba
        xmlns ="http://www.projekat.org/resenje"
        xmlns:res="http://www.projekat.org/resenje"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:pred="http://www.projekat.org/predicate/"
        about="http://www.projekat.org/resenje/`+this.broj+`"
        xsi:schemaLocation="http://www.projekat.org/resenje resenje.xsd"
        status="`+this.status+`"
        broj="`+this.broj+`"
        username="username"
        datum="`+this.datum+`"
        poverenikUsername="`+localStorage.getItem('username')+`">
        <res:broj xmlns:res ="http://www.projekat.org/resenje" property="pred:broj">`+this.broj+`</res:broj>
        <res:datum xmlns:res ="http://www.projekat.org/resenje" property="pred:datum">`+this.datum+`</res:datum>
        <res:status xmlns:res ="http://www.projekat.org/resenje" property="pred:status">`+this.status+`</res:status>
        <res:uvod>
            <res:paragraf>`+this.uvod+`</res:paragraf>
        </res:uvod>
        <res:sadrzaj>
            <res:resenje>
                <res:paragraf>`+this.resenje+`</res:paragraf>
            </res:resenje>
            <res:obrazlozenje>
                <res:paragraf>`+this.obrazlozenje+`</res:paragraf>
            </res:obrazlozenje>
            <res:poverenik>
                <res:ime xmlns:res ="http://www.projekat.org/resenje" property="pred:poverenikIme">`+this.poverenikIme+`</res:ime>
                <res:prezime xmlns:res ="http://www.projekat.org/resenje" property="pred:poverenikPrezime">`+this.poverenikPrezime+`</res:prezime>
            </res:poverenik>
        </res:sadrzaj>
    </res:zalba>`
    this.responseService.addResponse(xmlString, () => {
      this.router.navigateByUrl('/home');
    });
    
  }
}
