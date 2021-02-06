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

  myForm1 = new FormGroup({
    keywords: new FormControl(''),
  });
  
  constructor(private service: SilenceAppealService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.getSilenceAppeals();
  }
  
  getSilenceAppeals() {
    if (this.isAdmin()) {
      console.log("getappealsforadmin");
      this.service.getAppeals().subscribe((data: SAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    } 
  }

  sendExplanation(appeal: SAppealItem){
    console.log("sendExplanation = ", appeal);
    this.router.navigate(['/add-explanation/'+appeal.broj+"/silence/"+appeal.podnosiocUsername]);
  }

  submit1() {
    console.log("form = ", this.myForm1.value);
    if (this.myForm1.value.keywords == "") {
      this.getSilenceAppeals();
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
