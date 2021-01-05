import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { User } from '../../model/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  signupForm = this.formBuilder.group({
    username: ['', [Validators.required, Validators.pattern("")]],
    firstName: ['', [Validators.required, Validators.pattern("")]],
    lastName: ['', [Validators.required, Validators.pattern("")]],
    email: ['', [Validators.required, Validators.pattern("")]],
    password: ['', [Validators.required, Validators.pattern("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")]],
    passConfirm: ['', Validators.required]
  },
  {
    validator: this.MustMatch('password', 'passConfirm')
  });

  constructor(private service: UserService,
              private formBuilder: FormBuilder,
              private router: Router) { }

  signUp(): void{
    
    const user: User = new User();
    user.username = this.signupForm.value.username;
    user.password = this.signupForm.value.password;
    user.firstName = this.signupForm.value.firstName;
    user.lastName = this.signupForm.value.lastName;
    user.email = this.signupForm.value.email;
    console.log(user);

    this.service.signUp(user).subscribe(
      (response => {
        if (response !== null) {
          console.log("sign up successfully");
          this.router.navigateByUrl('/login');
        }
      }),
      (error => {
        console.log(error.error.message);
      })
    );
  }
  ngOnInit(): void {
  }

  MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
      const control = formGroup.controls[controlName];
      const matchingControl = formGroup.controls[matchingControlName];
      if (matchingControl.errors && !matchingControl.errors.mustMatch) {
        // return if another validator has already found an error on the matchingControl
        return;
      }
      // set error on matchingControl if validation fails
      if (control.value !== matchingControl.value) {
        matchingControl.setErrors({ mustMatch: true });
      } else {
        matchingControl.setErrors(null);
      }
    }
  }
}
