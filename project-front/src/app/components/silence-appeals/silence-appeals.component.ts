import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { SilenceAppealService } from '../../service/silence-appeal.service';
import { SAppealItem } from '../../model/silence-appeal.model';

@Component({
  selector: 'app-silence-appeals',
  templateUrl: './silence-appeals.component.html',
  styleUrls: ['./silence-appeals.component.css']
})
export class SilenceAppealsComponent implements OnInit {

  appeals: SAppealItem[] = [];

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
