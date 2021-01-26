import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { DecisionAppealService } from '../../service/decision-appeal.service';
import { DAppealItem } from '../../model/decision-appeal.model';

@Component({
  selector: 'app-decision-appeals',
  templateUrl: './decision-appeals.component.html',
  styleUrls: ['./decision-appeals.component.css']
})
export class DecisionAppealsComponent implements OnInit {

  appeals: DAppealItem[] = [];

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
  
  /*requestExplanation(appeal: DAppealItem){
    console.log("requestExplanation = ", appeal);
    this.service.requestExplanation(appeal.broj);
  }*/

  
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
