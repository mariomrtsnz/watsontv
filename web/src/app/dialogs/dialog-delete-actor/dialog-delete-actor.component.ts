import { ActorService } from './../../services/actor.service';
import { MatSnackBar, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-delete-actor',
  templateUrl: './dialog-delete-actor.component.html',
  styleUrls: ['./dialog-delete-actor.component.scss']
})
export class DialogDeleteActorComponent implements OnInit {
  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private actorService: ActorService, public dialogRef: MatDialogRef<DialogDeleteActorComponent>) { }

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
    this.actorService.remove(this.elementId).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to delete this actor.', 'Close', {duration: 3000}));
  }

}
