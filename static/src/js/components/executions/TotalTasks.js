import React from 'react';
import moment from 'moment';

const TotalTasks = React.createClass({
  render() {
    return (
    
         <div className="col-md-3 col-sm-6 col-xs-12">
          <div className="info-box">
            <span className="info-box-icon bg-yellow"><i className="ion-ios-list-outline"></i></span>

            <div className="info-box-content">
              <span className="info-box-text">Number of Tasks</span>
              <span className="info-box-number">{this.props.total}</span>
            </div>
           </div>
         </div>


    );
  }
});

export default TotalTasks;
