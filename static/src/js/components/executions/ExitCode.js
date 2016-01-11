import React from 'react';

const ExitCode = React.createClass({
    getText() {
        if (this.props.result == 0) {
            return "OK"
        } else  if (this.props.result == 0) {
            return "Possibly, errors in the playbook!"
        }else  if (this.props.result == 2) {
            return "Failed hosts!"
        }else  if (this.props.result == 3) {
            return "Dark hosts!"
        }
    }, 
    render() {
        return (
            <div className="col-md-3 col-sm-6 col-xs-12">
              <div className="info-box">
                <span className="info-box-icon bg-blue"><i className="ion-android-exit"></i></span>
                <div className="info-box-content">
                  <span className="info-box-text">Exit Code</span>
                  <span className="info-box-number">{this.props.result}</span>
                  <span className="info-box-text">{this.getText()}</span>
                </div>
               </div>
             </div>
        );
    }
});

export default ExitCode;
