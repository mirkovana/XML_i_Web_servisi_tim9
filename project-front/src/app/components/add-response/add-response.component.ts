import { Component, OnInit, Input } from '@angular/core';
import { ResponseService } from '../../service/response.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-response',
  templateUrl: './add-response.component.html',
  styleUrls: ['./add-response.component.css']
})
export class AddResponseComponent implements OnInit {
  zalbaSpec = '';
  textArea: string;
  username: string = "";
  broj: string = "";
  tip: string = "";

  status: string = "";
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
    this.broj = this.route.snapshot.paramMap.get('broj')
    this.username = this.route.snapshot.paramMap.get('username');
    this.tip = this.route.snapshot.paramMap.get('tip');
  }

  validate(){
    if(this.status == "" || this.datum == "" || this.uvod == "" || this.resenje == "" ||
        this.obrazlozenje == "" || this.poverenikIme == "" || this.poverenikPrezime == ""){
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
    if (date_regex.test(this.datum) == false) {
      Swal.fire({
        title: 'Error!',
        text: 'Pogresan format datuma => dd.mm.yyyy.',
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

    if (!this.validate()) {
      return;
    }

    let xmlString = `<?xml version="1.0" encoding="UTF-8"?>
    <res:zalba
        xmlns ="http://www.projekat.org/resenje"
        xmlns:res="http://www.projekat.org/resenje"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:pred="http://www.projekat.org/predicate/"
        about="http://www.projekat.org/resenje/`+ this.broj + `"
        xsi:schemaLocation="http://www.projekat.org/resenje resenje.xsd"
        status="`+ this.status + `"
        broj="`+ this.broj + `"
        username="`+ this.username + `"
        datum="`+ this.datum + `"
        poverenikUsername="`+ localStorage.getItem('username') + `">
        <res:broj xmlns:res ="http://www.projekat.org/resenje" property="pred:broj">`+ this.broj + `</res:broj>
        <res:datum xmlns:res ="http://www.projekat.org/resenje" property="pred:datum">`+ this.datum + `</res:datum>
        <res:status xmlns:res ="http://www.projekat.org/resenje" property="pred:status">`+ this.status + `</res:status>
        <res:uvod>
            <res:paragraf>`+ this.uvod + `</res:paragraf>
        </res:uvod>
        <res:sadrzaj>
            <res:resenje>
                <res:paragraf>`+ this.resenje + `</res:paragraf>
            </res:resenje>
            <res:obrazlozenje>
                <res:paragraf>`+ this.obrazlozenje + `</res:paragraf>
            </res:obrazlozenje>
            <res:poverenik>
                <res:ime xmlns:res ="http://www.projekat.org/resenje" property="pred:poverenikIme">`+ this.poverenikIme + `</res:ime>
                <res:prezime xmlns:res ="http://www.projekat.org/resenje" property="pred:poverenikPrezime">`+ this.poverenikPrezime + `</res:prezime>
            </res:poverenik>
        </res:sadrzaj>
    </res:zalba>`
    this.responseService.addResponse(xmlString, this.tip, () => {
      this.router.navigateByUrl('/responses');
    });

  }
}
