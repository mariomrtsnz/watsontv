import { MediaService } from './../../services/media.service';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-delete-media',
  templateUrl: './dialog-delete-media.component.html',
  styleUrls: ['./dialog-delete-media.component.scss']
})
export class DialogDeleteMediaComponent implements OnInit {

  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private mediaService: MediaService, public dialogRef: MatDialogRef<DialogDeleteMediaComponent>) { }

  ngOnInit() {
    this.elementId = this.data.mediaId;
    this.elementName = this.data.mediaTitle;
  }

  captcha() {
    if (this.checkedRobot) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close('cancel');
  }

  delete() {
    if (this.mediaService.mediaType.toLowerCase() === 'series') {
      this.mediaService.removeSeries(this.elementId).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when trying to delete this Series.', 'Close', {duration: 3000}));
    } else {
      this.mediaService.removeMovie(this.elementId).subscribe(result => {
        this.dialogRef.close('confirm');
      }, error => this.snackBar.open('There was an error when trying to delete this Movie.', 'Close', {duration: 3000}));
    }
  }

}
