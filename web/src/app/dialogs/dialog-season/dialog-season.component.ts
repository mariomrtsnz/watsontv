import { MediaService } from './../../services/media.service';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, Inject } from '@angular/core';
import { SeasonService } from 'src/app/services/season.service';
import { SeasonDto } from 'src/app/dto/season-dto';

@Component({
  selector: 'app-dialog-season',
  templateUrl: './dialog-season.component.html',
  styleUrls: ['./dialog-season.component.scss']
})
export class DialogSeasonComponent implements OnInit {

  edit: boolean;
  number: number;
  seasonId: string;
  series: string;
  form: FormGroup;

  // tslint:disable-next-line:max-line-length
  constructor(private snackBar: MatSnackBar, private fb: FormBuilder, @Inject(MAT_DIALOG_DATA) public data: any, private mediaService: MediaService, private seasonService: SeasonService, public dialogRef: MatDialogRef<DialogSeasonComponent>) { }

  ngOnInit() {
    this.series = this.mediaService.selectedMedia.id;
    this.createForm();
    if (this.data) {
      this.edit = true;
      this.seasonId = this.data.season.id;
    } else {
      this.edit = false;
    }
  }

  onSubmit() {
    if (this.edit) {
      const editedSeason: SeasonDto = <SeasonDto>this.form.value;
      this.seasonService.edit(this.seasonId, editedSeason).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to edit this Season.', 'Close', { duration: 3000 }));
    } else {
      const newSeason: SeasonDto = <SeasonDto>this.form.value;
      this.seasonService.create(newSeason).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when were trying to create this Season.', 'Close', { duration: 3000 }));
    }
  }

  createForm() {
    if (this.data) {
      const editForm: FormGroup = this.fb.group({
        number: [this.data.season.number, Validators.compose([Validators.required])],
        series: [this.series, Validators.compose([Validators.required])]
      });
      this.form = editForm;
    } else {
      const newForm: FormGroup = this.fb.group({
        number: [null, Validators.compose([Validators.required])],
        series: [this.series, Validators.compose([Validators.required])]
      });
      this.form = newForm;
    }
  }
}
