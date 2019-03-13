import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { EpisodeService } from './../../services/episode.service';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-delete-episode',
  templateUrl: './dialog-delete-episode.component.html',
  styleUrls: ['./dialog-delete-episode.component.scss']
})
export class DialogDeleteEpisodeComponent implements OnInit {

  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private episodeService: EpisodeService, public dialogRef: MatDialogRef<DialogDeleteEpisodeComponent>) { }

  ngOnInit() {
    this.elementId = this.data.actorId;
    this.elementName = this.data.actorName;
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
    this.episodeService.remove(this.elementId).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to delete this Episode.', 'Close', {duration: 3000}));
  }

}
