import { MAT_DIALOG_DATA, MatSnackBar, MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import { SeasonService } from 'src/app/services/season.service';

@Component({
  selector: 'app-dialog-delete-season',
  templateUrl: './dialog-delete-season.component.html',
  styleUrls: ['./dialog-delete-season.component.scss']
})
export class DialogDeleteSeasonComponent implements OnInit {

  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private seasonService: SeasonService, public dialogRef: MatDialogRef<DialogDeleteSeasonComponent>) { }

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
    this.seasonService.remove(this.elementId).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to delete this Season.', 'Close', {duration: 3000}));
  }

}
