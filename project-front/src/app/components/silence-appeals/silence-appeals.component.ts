import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { SAppealItem } from '../../model/silence-appeal.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-silence-appeals',
  templateUrl: './silence-appeals.component.html',
  styleUrls: ['./silence-appeals.component.css']
})
export class SilenceAppealsComponent implements OnInit {

  appeals: SAppealItem[] = [];

  myForm = new FormGroup({
    broj: new FormControl(''),
    datum: new FormControl(''),
    status: new FormControl(''),
    ime: new FormControl(''),
    prezime: new FormControl(''),
    nazivOrgana: new FormControl(''),
    mesto: new FormControl(''),
  });

  constructor(private service: SilenceAppealService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getSilenceAppeals();
  }
  
  getSilenceAppeals() {
    if (this.isUser()) {
      console.log("getappealsforuser");
      this.service.getAppealsForUsername(this.getUsername()).subscribe((data: SAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    } else {
      console.log("getappealsforadmin");
      this.service.getAppeals().subscribe((data: SAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    }
  }

  requestExplanation(appeal: SAppealItem){
    console.log("requestExplanation = ", appeal);
    this.service.requestExplanation(appeal.broj).subscribe((data: any)  => {
      console.log("requestExplanation success = ", data);
      this.getSilenceAppeals();
    }, error => {
      //TODO ovde vraca gresku bez razloga
      console.log("error = ", error);
      this.getSilenceAppeals();
    });
  }

  deleteAppeal(appeal: SAppealItem){
    console.log("deleteappeal = ", appeal);
    this.service.deleteAppeal(appeal.broj, ()=>{
      this.appeals = this.appeals.filter(item => item.broj != appeal.broj);
    });
  }

  sendResponse(appeal:SAppealItem){
    console.log("sendresponse = ", appeal);
    this.router.navigate(['/add-response/'+appeal.broj+'/'+appeal.podnosiocUsername+'/silence']);
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
      this.getSilenceAppeals();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <sAppealSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <broj>`+this.myForm.value.broj+`</broj>
        <datum>`+this.myForm.value.datum+`</datum>
        <mesto>`+this.myForm.value.mesto+`</mesto>
        <ime>`+this.myForm.value.ime+`</ime>
        <prezime>`+this.myForm.value.prezime+`</prezime>
        <organVlasti>`+this.myForm.value.nazivOrgana+`</organVlasti>
        <status>`+this.myForm.value.status+`</status>
    </sAppealSearch>`;

    this.service.searchByMetadata(xml).subscribe((data: any)  => {
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
