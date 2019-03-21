import { Observable, forkJoin } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ActorDto } from './../../dto/actor-dto';
import { ActorService } from './../../services/actor.service';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { UploadService } from 'src/app/services/upload.service';

const URL = `${environment.apiUrl}/actors/picture`;

@Component({
  selector: 'app-dialog-actor',
  templateUrl: './dialog-actor.component.html',
  styleUrls: ['./dialog-actor.component.scss']
})
export class DialogActorComponent implements OnInit {
  @ViewChild('file') file;
  progress;
  edit: boolean;
  name: string;
  picture: string;
  actorId: string;
  form: FormGroup;
  urlImage: string;
  uploading = false;
  uploadSuccessful = false;
  public files: Set<File> = new Set();
  canBeClosed = true;
  primaryButtonText = 'Upload Picture';
  showCancelButton = true;

  // tslint:disable-next-line:max-line-length
  constructor(private uploadService: UploadService, private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private actorService: ActorService, public dialogRef: MatDialogRef<DialogActorComponent>) {}

  ngOnInit() {
    this.createForm();
    if (this.data) {
      this.edit = true;
      this.actorId = this.data.actor.id;
    } else {
      this.edit = false;
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        name: [this.data.actor.name, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        name: [null, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }

  addActor() {
    const actorCreateDto = new ActorDto(this.name, this.picture);
    this.actorService.create(actorCreateDto).subscribe(
      actor => {
        this.dialogRef.close('confirm');
      }
    );
  }

  editActor() {
    const actorEditDto = new ActorDto(this.name, this.picture);
    this.actorService.edit(this.actorId, actorEditDto).subscribe(
      actor => {
        this.dialogRef.close('confirm');
      }
    );
  }

  onSubmit() {
    if (this.edit) {
      const editedActor: ActorDto = <ActorDto>this.form.value;
      this.actorService.edit(this.actorId, editedActor).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this actor.', 'Close', { duration: 3000 }));
    }
  }

  onFilesAdded() {
    const files: { [key: string]: File } = this.file.nativeElement.files;
    this.files = new Set();
    for (const key in files) {
      if (!isNaN(parseInt(key, 10))) {
        this.files.add(files[key]);
      }
    }
  }

  closeDialog() {
    if (this.uploadSuccessful) {
      return this.dialogRef.close();
    }
    this.uploading = true;
    this.progress = this.uploadService.upload(this.files, this.form.value);

    // tslint:disable-next-line:forin
    for (const key in this.progress) {
      this.progress[key].progress.subscribe(val => console.log(val));
    }
    const allProgressObservables = [];
    // tslint:disable-next-line:forin
    for (const key in this.progress) {
      allProgressObservables.push(this.progress[key].progress);
    }
    this.primaryButtonText = 'End';
    this.canBeClosed = false;
    this.dialogRef.disableClose = true;
    this.showCancelButton = false;
    forkJoin(allProgressObservables).subscribe(end => {
      this.canBeClosed = true;
      this.dialogRef.disableClose = false;
      this.uploadSuccessful = true;
      this.uploading = false;
    });
  }

}
