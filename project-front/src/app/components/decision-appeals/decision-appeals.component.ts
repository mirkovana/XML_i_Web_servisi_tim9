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

  myForm = new FormGroup({
    broj: new FormControl(''),
    datum: new FormControl(''),
    status: new FormControl(''),
    ime: new FormControl(''),
    prezime: new FormControl(''),
    nazivOrgana: new FormControl(''),
    mesto: new FormControl(''),
  });
  
  constructor(private service: DecisionAppealService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getDecisionAppeals();
  }

  getDecisionAppeals() {
    if (this.isUser()) {
      console.log("getappealsforuser");
      this.service.getAppealsForUsername(this.getUsername()).subscribe((data: DAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    }else{
      console.log("getappealsforadmin");
      this.service.getAppeals().subscribe((data: DAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    }
  }

  deleteAppeal(appeal: DAppealItem){
    console.log("deleteappeal = ", appeal);
    this.service.deleteAppeal(appeal.broj, ()=>{
      this.appeals = this.appeals.filter(item => item.broj != appeal.broj);
    });
  }
  
  submit() {
    console.log("form = ",  this.myForm.value);
    if(this.myForm.value.broj=="" 
      && this.myForm.value.datum==""
      && this.myForm.value.ime==""
      && this.myForm.value.prezime==""
      && this.myForm.value.mesto==""
      && this.myForm.value.nazivOrgana==""
      && this.myForm.value.status==""){
      this.getDecisionAppeals();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <dAppealSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <broj>`+this.myForm.value.broj+`</broj>
        <datum>`+this.myForm.value.datum+`</datum>
        <mesto>`+this.myForm.value.mesto+`</mesto>
        <ime>`+this.myForm.value.ime+`</ime>
        <prezime>`+this.myForm.value.prezime+`</prezime>
        <organVlasti>`+this.myForm.value.nazivOrgana+`</organVlasti>
        <status>`+this.myForm.value.status+`</status>
    </dAppealSearch>`;

    this.service.searchByMetadata(xml).subscribe((data: any)  => {
      console.log("data = ", data);
      this.appeals = data;
    }, error => {
      console.log(error);
    });
  }
  
  requestExplanation(appeal: DAppealItem){
    console.log("requestExplanation = ", appeal);
    this.service.requestExplanation(appeal.broj).subscribe((data: any)  => {
      console.log("requestExplanation success = ", data);
      this.getDecisionAppeals();
    }, error => {
      //TODO ovde vraca gresku bez razloga
      console.log("error = ", error);
      this.getDecisionAppeals();
    });
  }

  sendResponse(appeal:DAppealItem){
    console.log("sendresponse = ", appeal);
    this.router.navigate(['/add-response/'+appeal.broj+'/'+appeal.podnosiocUsername+'/decision']);
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
