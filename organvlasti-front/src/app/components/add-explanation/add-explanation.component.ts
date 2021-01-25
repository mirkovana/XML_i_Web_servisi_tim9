import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ExplanationService } from '../../service/explanation.service';

@Component({
  selector: 'app-add-explanation',
  templateUrl: './add-explanation.component.html',
  styleUrls: ['./add-explanation.component.css']
})
export class AddExplanationComponent implements OnInit {

  broj: string;
  tip: string;
  username: string;

  obrazlozenje: string;
  ime: string;
  prezime: string;
  potpis: string;
  mesto: string;
  dana: string;

  constructor(private service: ExplanationService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.broj = this.route.snapshot.paramMap.get('broj')
    this.tip = this.route.snapshot.paramMap.get('tip')
    this.username = this.route.snapshot.paramMap.get('username')
  }

  sendFile() {
    console.log("sendFile");

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
      if(this.tip === "decision"){
        this.router.navigateByUrl('/decision-appeals');
      }else if (this.tip === "silence"){
        this.router.navigateByUrl('/silence-appeals');  
      }
    });
  }
}
