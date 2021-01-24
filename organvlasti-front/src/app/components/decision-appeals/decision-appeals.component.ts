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
    if (this.isAdmin()) {
      this.service.getAppeals().subscribe((data: DAppealItem[]) => {
        console.log("component subscribe = ", data);
        this.appeals = data;
      }, error => {
        console.log("error = ", error);
      });
    }
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
