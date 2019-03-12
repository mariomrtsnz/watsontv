import { ActorDto } from './../../dto/actor-dto';
import { ActorService } from './../../services/actor.service';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-actor',
  templateUrl: './dialog-actor.component.html',
  styleUrls: ['./dialog-actor.component.scss']
})
export class DialogActorComponent implements OnInit {

  edit: boolean;
  name: string;
  picture: string;
  actorId: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private actorService: ActorService, public dialogRef: MatDialogRef<DialogActorComponent>) { }

  ngOnInit() {
    this.createForm();
    if (this.data.actor) {
      this.edit = true;
      this.actorId = this.data.actor.id;
    } else {
      this.edit = false;
    }
  }

  onSubmit() {
    if (this.edit) {
      const editedActor: ActorDto = <ActorDto>this.form.value;
      this.actorService.edit(this.actorId, editedActor).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this actor.', 'Close', { duration: 3000 }));
    } else {
      const newActor: ActorDto = <ActorDto>this.form.value;
      this.actorService.create(newActor).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to create this actor.', 'Close', { duration: 3000 }));
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        name: [this.data.actor.name, Validators.compose([Validators.required])],
        picture: [this.data.actor.picture, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        name: [null, Validators.compose([Validators.required])],
        picture: [null, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }

  addGenre() {
    const actorCreateDto = new ActorDto(this.name, this.picture);
    this.actorService.create(actorCreateDto).subscribe(
      actor => {
        this.dialogRef.close('confirm');
      }
    );
  }

  editGenre() {
    const actorEditDto = new ActorDto(this.name, this.picture);
    this.actorService.edit(this.actorId, actorEditDto).subscribe(
      actor => {
        this.dialogRef.close('confirm');
      }
    );
  }

}
