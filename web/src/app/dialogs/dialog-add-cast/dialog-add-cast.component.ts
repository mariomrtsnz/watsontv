import { MediaService } from './../../services/media.service';
import { MatDialogRef, MatSnackBar } from '@angular/material';
import { ActorService } from './../../services/actor.service';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { OneActorResponse } from 'src/app/interfaces/one-actor-response';

@Component({
  selector: 'app-dialog-add-cast',
  templateUrl: './dialog-add-cast.component.html',
  styleUrls: ['./dialog-add-cast.component.scss']
})
export class DialogAddCastComponent implements OnInit {

  actors: OneActorResponse[];
  selectedMediaType: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private fb: FormBuilder, private router: Router, private mediaService: MediaService, private actorService: ActorService, public dialogRef: MatDialogRef<DialogAddCastComponent>, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.createForm();
  }

  onSubmit() {
    this.mediaService.addCastMember(this.form.value.actor).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to add this Actor.', 'Close', {duration: 3000}));
  }

  createForm() {
    this.actorService.getAllSortedByName().subscribe(result => {
      this.actors = result.rows;
      this.form = this.fb.group({
        actor: [null, Validators.compose([Validators.required])]
      });
    });
  }

}
