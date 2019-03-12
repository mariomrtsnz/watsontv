import { CollectionService } from './../../services/collection.service';
import { MAT_DIALOG_DATA, MatSnackBar, MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-delete-collection',
  templateUrl: './dialog-delete-collection.component.html',
  styleUrls: ['./dialog-delete-collection.component.scss']
})
export class DialogDeleteCollectionComponent implements OnInit {

  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private collectionService: CollectionService, public dialogRef: MatDialogRef<DialogDeleteCollectionComponent>) { }

  ngOnInit() {
    this.elementId = this.data.collectionId;
    this.elementName = this.data.collectionName;
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
    this.collectionService.remove(this.elementId).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to delete this collection.', 'Close', {duration: 3000}));
  }

}
