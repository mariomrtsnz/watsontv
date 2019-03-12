import { GenreService } from './../../services/genre.service';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MatSnackBar, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-dialog-delete-genre',
  templateUrl: './dialog-delete-genre.component.html',
  styleUrls: ['./dialog-delete-genre.component.scss']
})
export class DialogDeleteGenreComponent implements OnInit {
  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private genreService: GenreService, public dialogRef: MatDialogRef<DialogDeleteGenreComponent>) { }

  ngOnInit() {
    this.elementId = this.data.genreId;
    this.elementName = this.data.genreName;
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
    this.genreService.remove(this.elementId).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to delete this route.', 'Close', {duration: 3000}));
  }
}
