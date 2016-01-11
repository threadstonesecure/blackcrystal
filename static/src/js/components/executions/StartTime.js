import React from 'react';
import moment from 'moment';

const StartTime = React.createClass({
  date(){
    return moment(this.props.time).format('MMMM Do YYYY');
  },

  time(){
    return moment(this.props.time).format('h:mm a');
  },
  render() {
    return (
    
         <div className="col-md-3 col-sm-6 col-xs-12">
          <div className="info-box">
            <span className="info-box-icon bg-yellow"><i className="ion-ios-calendar-outline"></i></span>

            <div className="info-box-content">
              <span className="info-box-text">Started at</span>
              <span className="info-box-text">{this.date()}</span>
              <span className="info-box-number">{this.time()}</span>
            </div>
           </div>
         </div>


    );
  }
});

export default StartTime;
