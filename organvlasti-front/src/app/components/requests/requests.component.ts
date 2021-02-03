import { Component, OnInit } from '@angular/core';
import { RequestItem } from 'src/app/model/request.model';
import { RequestService } from '../../service/request.service';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {

  requests: Array<RequestItem> = [];

  myForm = new FormGroup({
    broj: new FormControl(''),
    datum: new FormControl(''),
    ime: new FormControl(''),
    prezime: new FormControl(''),
    nazivOrgana: new FormControl(''),
    sediste: new FormControl(''),
    status: new FormControl(''),
  });

  myForm1 = new FormGroup({
    keywords: new FormControl(''),
  });

  constructor(private service: RequestService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    console.log("get requests");
    this.getRequests();
  }

  submit1() {
    console.log("form = ",  this.myForm1.value);
    if(this.myForm1.value.keywords==""){
      this.getRequests();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <keywordSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <keywords>`+this.myForm1.value.keywords+`</keywords>
    </keywordSearch>`;

    this.service.searchByKeywords(xml).subscribe((data: any)  => {
      console.log("data = ", data);
      this.requests = data;
    }, error => {
      console.log(error);
    });

  }
  
  submit() {
    console.log("form = ",  this.myForm.value);
    if(this.myForm.value.broj=="" 
    && this.myForm.value.datum==""
    && this.myForm.value.ime==""
    && this.myForm.value.prezime==""
    && this.myForm.value.nazivOrgana==""
    && this.myForm.value.sediste==""
    && this.myForm.value.status==""){
      this.getRequests();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <zahtevSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <broj>`+this.myForm.value.broj+`</broj>
        <datum>`+this.myForm.value.datum+`</datum>
        <ime>`+this.myForm.value.ime+`</ime>
        <prezime>`+this.myForm.value.prezime+`</prezime>
        <nazivOrgana>`+this.myForm.value.nazivOrgana+`</nazivOrgana>
        <sediste>`+this.myForm.value.sediste+`</sediste>
        <status>`+this.myForm.value.status+`</status>
    </zahtevSearch>`;

    this.service.searchByMetadata(xml).subscribe((data: any)  => {
      console.log("data = ", data);
      this.requests = data;
    }, error => {
      console.log(error);
    });
  }

  getRequests(){
    if(this.userService.isUser()){
      console.log("isUser");
      this.service.getRequestsForUser(this.getUsername()).subscribe((data: RequestItem[])  => {
        console.log("success");
        console.log(data);
        this.requests = data;
      }, error => {
        console.log(error);
      });
    }else if(this.userService.isAdmin()){
      console.log("isAdmin");
      this.service.getRequests().subscribe((data: RequestItem[])  => {
        console.log(data);
        this.requests = data;
      }, error => {
        console.log(error);
      });;
    }
  }

  showNotice(request: RequestItem){
    
  }

  replyRequest(request: RequestItem){
    console.log("reply = ", request);
    this.router.navigate(['/add-notice/'+request.username+"/"+request.broj]);
  }

  denyRequest(request: RequestItem){
    console.log("deny = ", request);
    this.service.denyRequest(request.broj).subscribe((data: any)  => {
      console.log(data);
      console.log("request denied");
      this.getRequests();
    }, error => {
      //TODO ovde nekako uvek baca gresku iako je promena zahteva u bazi uspesna
      this.getRequests();
      console.log(error);
    });
  }

  convertTimestamp(time: string): string {
    let date = new Date(parseInt(time));
    return date.toLocaleString();
  }

  deleteRequest(request: RequestItem){
    console.log("delete = ", request);
    this.service.deleteRequest(request.broj, ()=> {
      this.requests = this.requests.filter(item => item.broj != request.broj);
    });
  }

  isUser(){
    return this.userService.isUser();
  }
  
  isAdmin(){
    return this.userService.isAdmin();
  }

  getUsername(){
    return this.userService.getUsername();
  }
}
