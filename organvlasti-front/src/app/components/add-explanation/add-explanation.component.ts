import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExplanationService } from '../../service/explanation.service';
import Swal from "sweetalert2";

@Component({
  selector: 'app-add-explanation',
  templateUrl: './add-explanation.component.html',
  styleUrls: ['./add-explanation.component.css']
})
export class AddExplanationComponent implements OnInit {

  broj: string;
  tip: string;
  username: string;

  obrazlozenje: string = "";
  ime: string = "";
  prezime: string = "";
  potpis: string = "";
  mesto: string = "";
  dana: string = "";

  constructor(private service: ExplanationService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.broj = this.route.snapshot.paramMap.get('broj')
    this.tip = this.route.snapshot.paramMap.get('tip')
    this.username = this.route.snapshot.paramMap.get('username')
  }

  validate() {
    if (this.obrazlozenje == "" || this.ime == "" || this.prezime == "" || this.potpis == "" ||
      this.mesto == "" || this.dana == "") {
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
    if(date_regex.test(this.dana) == false){
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

    let xmlSpec: string = `<?xml version="1.0" encoding="UTF-8"?>
    <ex:explanation 
        xmlns:ex="http://www.projekat.org/explanation"
        xmlns:pred="http://www.projekat.org/predicate/"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"    
        xsi:schemaLocation="http://www.projekat.org/explanation explanation.xsd"
        broj="`+ this.broj + `" 
        tipZalbe="`+ this.tip + `"
        username="`+ this.username + `"
        organVlastiUsername="`+ localStorage.getItem('username') + `" 
        about="http://www.projekat.org/explanation/`+ this.broj + `">
        <ex:broj property="pred:broj">`+ this.broj + `</ex:broj>
        <ex:username property="pred:username">`+ this.username + `</ex:username>
        <ex:organVlastiUsername property="pred:organVlastiUsername">`+ localStorage.getItem('username') + `</ex:organVlastiUsername>
        <ex:text>`+ this.obrazlozenje + `</ex:text>
        <ex:mesto>`+ this.mesto + `</ex:mesto>
        <ex:datum property="pred:datum">`+ this.dana + `</ex:datum>
        <ex:ime>`+ this.ime + `</ex:ime>
        <ex:prezime>`+ this.prezime + `</ex:prezime>
        <ex:potpis>`+ this.potpis + `</ex:potpis>
    </ex:explanation>`;

    console.log("xmlString = ", xmlSpec);
    this.service.addExplanation(xmlSpec, () => {
      if (this.tip === "decision") {
        this.router.navigateByUrl('/decision-appeals');
      } else if (this.tip === "silence") {
        this.router.navigateByUrl('/silence-appeals');
      }
    });
  }
}
