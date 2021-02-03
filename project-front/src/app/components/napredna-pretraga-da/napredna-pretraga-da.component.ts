import { Component, OnInit } from '@angular/core';
import { DAppealItem } from 'src/app/model/decision-appeal.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DecisionAppealService } from 'src/app/service/decision-appeal.service';
@Component({
  selector: 'app-napredna-pretraga-da',
  templateUrl: './napredna-pretraga-da.component.html',
  styleUrls: ['./napredna-pretraga-da.component.css']
})
export class NaprednaPretragaDaComponent implements OnInit {

  naprednaPretraga: FormGroup;
  organVlasti: string = '';
  broj: string = '';
  ime: string = '';
  prezime: string = '';
  datum: string = '';
  mesto: string = '';
  zalbe: DAppealItem[] = new Array();

  constructor(private formBuilder: FormBuilder,private service:DecisionAppealService ) { }

  ngOnInit(): void {
    this.naprednaPretraga = this.formBuilder.group({
      organVlasti: [this.organVlasti, Validators.required],
      ime: [this.ime, Validators.required],
      prezime: [this.prezime, Validators.required],
      datum: [this.datum, Validators.required],
      broj: [this.broj, Validators.required],
      mesto: [this.mesto, Validators.required]
    })
  }


  onSubmit(){
    this.service.naprednaPretraga(
      this.naprednaPretraga.get("organVlasti").value, 
      this.naprednaPretraga.get("mesto").value,
      this.naprednaPretraga.get("ime").value, 
      this.naprednaPretraga.get("prezime").value, 
      this.naprednaPretraga.get("datum").value, 
      this.naprednaPretraga.get("broj").value
      ).subscribe(data=>{
        console.log(data);
        this.zalbe = data;
        for (let index = 0; index < data.length; index++) {
         let ob:DAppealItem = data[index];
         console.log(ob);
          
        }
      });
  }

}
