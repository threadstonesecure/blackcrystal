import React from 'react';
import juration from 'juration';

const Duration = React.createClass({
  humanize(duration){
    if (duration == 0) {
      duration = 1;
    }
    return juration.stringify(duration, {format: 'long'});
  },

  render() {
    return (
       <div className="col-md-3 col-sm-6 col-xs-12">
          <div className="info-box">
            <span className="info-box-icon bg-aqua"><i className="ion-ios-stopwatch-outline"></i></span>

            <div className="info-box-content">
              <span className="info-box-text">Completed in</span>
              <span className="info-box-number">{this.humanize(this.props.duration)}</span>
            </div>
           </div>
         </div>
    );
  }
});

export default Duration;
