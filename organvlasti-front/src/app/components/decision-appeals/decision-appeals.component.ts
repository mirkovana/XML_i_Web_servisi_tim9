import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { DecisionAppealService } from '../../service/decision-appeal.service';
import { DAppealItem } from '../../model/decision-appeal.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-decision-appeals',
  templateUrl: './decision-appeals.component.html',
  styleUrls: ['./decision-appeals.component.css']
})
export class DecisionAppealsComponent implements OnInit {

  appeals: DAppealItem[] = [];

  myForm1 = new FormGroup({
    keywords: new FormControl(''),
  });
  
  constructor(private service: DecisionAppealService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getDecisionAppeals();
  }

  getDecisionAppeals() {
    if (this.isAdmin()) {
      this.service.getAppeals().subscribe((data: DAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    }
  }

  sendExplanation(appeal: DAppealItem){
    console.log("sendExplanation = ", appeal);
    this.router.navigate(['/add-explanation/'+appeal.broj+"/decision/"+appeal.podnosiocUsername]);
  }

  submit1() {
    console.log("form = ", this.myForm1.value);
    if (this.myForm1.value.keywords == "") {
      this.getDecisionAppeals();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <keywordSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <keywords>`+ this.myForm1.value.keywords + `</keywords>
    </keywordSearch>`;

    this.service.searchByKeywords(xml).subscribe((data: any) => {
      console.log("data = ", data);
      this.appeals = data;
    }, error => {
      console.log(error);
    });
  }

  isUser() {
    return this.userService.isUser();
  }

  isAdmin() {
    return this.userService.isAdmin();
  }

  getUsername() {
    return this.userService.getUsername();
  }
}
