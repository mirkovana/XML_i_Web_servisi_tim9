import { Component, OnInit } from '@angular/core';
import { NoticeItem } from 'src/app/model/notice.model';
import { NoticeService } from '../../service/notice.service';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-notices',
  templateUrl: './notices.component.html',
  styleUrls: ['./notices.component.css']
})
export class NoticesComponent implements OnInit {

  notices: Array<NoticeItem> = [];
  naprednaPretraga: FormGroup;

  nazivOrgana: string = '';
  sedisteOrgana: string = '';
  ime: string = '';
  prezime: string = '';
  datum: string = '';
  brojPredmeta: string = '';

  myForm = new FormGroup({
    broj: new FormControl(''),
    datum: new FormControl(''),
    ime: new FormControl(''),
    prezime: new FormControl(''),
    nazivOrgana: new FormControl(''),
    sediste: new FormControl(''),
  });

  myForm1 = new FormGroup({
    keywords: new FormControl(''),
  });

  constructor(private service: NoticeService,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private noticeService: NoticeService,
              private router: Router) { }

  ngOnInit(): void {
    this.naprednaPretraga = this.formBuilder.group({
      nazivOrgana: [this.nazivOrgana, Validators.required],
      sedisteOrgana: [this.sedisteOrgana, Validators.required],
      ime: [this.ime, Validators.required],
      prezime: [this.prezime, Validators.required],
      datum: [this.datum, Validators.required],
      brojPredmeta: [this.brojPredmeta, Validators.required]
    })
    console.log("get requests");
    this.getNotices();
    this.myForm1.get("keywords").reset();
  }

  getNotices(){
    if(this.userService.isUser()){
      console.log("isUser");
      this.service.getNoticesForUser(this.getUsername()).subscribe((data: NoticeItem[])  => {
        console.log("success");
        console.log(data);
        this.notices = data;
      }, error => {
        console.log(error);
      });
    }else if(this.userService.isAdmin()){
      console.log("isAdmin");
      this.service.getNotices().subscribe((data: NoticeItem[])  => {
        console.log(data);
        this.notices = data;
      }, error => {
        console.log(error);
      });;
    }
  }

  submit1() {
    console.log("form = ",  this.myForm1.value);
    if(this.myForm1.value.keywords==""){
      this.getNotices();
      return;
    }
    let xml = `<?xml version="1.0" encoding="UTF-8"?>
    <keywordSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        <keywords>`+this.myForm1.value.keywords+`</keywords>
    </keywordSearch>`;

    this.service.searchByKeywords(xml).subscribe((data: any)  => {
      console.log("data = ", data);
      this.notices = data;
    }, error => {
      console.log(error);
    });

  }

  napredna(){
    this.noticeService.naprednaPretraga(
      this.naprednaPretraga.get("nazivOrgana").value, 
      this.naprednaPretraga.get("sedisteOrgana").value,
      this.naprednaPretraga.get("ime").value, 
      this.naprednaPretraga.get("prezime").value, 
      this.naprednaPretraga.get("datum").value, 
      this.naprednaPretraga.get("brojPredmeta").value
      ).subscribe(data=>{
        console.log(data);
        this.notices = data;
        for (let index = 0; index < data.length; index++) {
         let ob:NoticeItem = data[index];
         console.log(ob);
          
        }
      });
  }
  
  // submit() {
  //   console.log("form = ",  this.myForm.value);
  //   if(this.myForm.value.broj=="" 
  //   && this.myForm.value.datum==""
  //   && this.myForm.value.ime==""
  //   && this.myForm.value.prezime==""
  //   && this.myForm.value.nazivOrgana==""
  //   && this.myForm.value.sediste==""){
  //     this.getNotices();
  //     return;
  //   }
  //   let xml = `<?xml version="1.0" encoding="UTF-8"?>
  //   <obavestenjeSearch xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  //       <brojPredmeta>`+this.myForm.value.broj+`</brojPredmeta>
  //       <datum>`+this.myForm.value.datum+`</datum>
  //       <ime>`+this.myForm.value.ime+`</ime>
  //       <prezime>`+this.myForm.value.prezime+`</prezime>
  //       <nazivOrgana>`+this.myForm.value.nazivOrgana+`</nazivOrgana>
  //       <sedisteOrgana>`+this.myForm.value.sediste+`</sedisteOrgana>
  //   </obavestenjeSearch>`;

  //   this.service.searchByMetadata(xml).subscribe((data: any)  => {
  //     console.log("data = ", data);
  //     this.notices = data;
  //   }, error => {
  //     console.log(error);
  //   });
  // }

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
