import { Component, OnInit, Input } from '@angular/core';
import { XonomyModel } from "../../model/xonomy.model";
import { DecisionAppealService } from '../../service/decision-appeal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DecisionAppealDTO } from "../../model/decision-appeal.model";

declare const Xonomy: any;
@Component({
  selector: 'app-add-decision-appeal',
  templateUrl: './add-decision-appeal.component.html',
  styleUrls: ['./add-decision-appeal.component.css']
})
export class AddDecisionAppealComponent implements OnInit {

  appealSpec = '';
  textArea: string;

  constructor(private service: DecisionAppealService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  sendFile() {
    console.log("sendFile");
    
    let obj: DecisionAppealDTO = {
      "text" : this.textArea
    };

    console.log(obj);
    this.service.addDecisionAppeal(obj).subscribe(
      response => {
        console.log("added appeal");
        this.router.navigateByUrl('/home');
      },
      error => {
        console.log(error);
      }
    );
  }

}
