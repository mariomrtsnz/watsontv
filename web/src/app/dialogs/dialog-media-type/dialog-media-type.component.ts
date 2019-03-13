import { MatDialogRef } from '@angular/material';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MediaService } from 'src/app/services/media.service';

@Component({
  selector: 'app-dialog-media-type',
  templateUrl: './dialog-media-type.component.html',
  styleUrls: ['./dialog-media-type.component.scss']
})
export class DialogMediaTypeComponent implements OnInit {

  mediaTypes = ['Series', 'Movie'];
  selectedMediaType: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private fb: FormBuilder, private router: Router, private mediaService: MediaService, public dialogRef: MatDialogRef<DialogMediaTypeComponent>) { }

  ngOnInit() {
    this.createForm();
  }

  onSubmit() {
    this.dialogRef.close(this.form.value.type);
  }

  createForm() {
    this.form = this.fb.group({
      type: [null, Validators.compose([Validators.required])]
    });
  }

}
