import React from 'react';
import { Row, Grid } from 'react-bootstrap';
import LinkedStateMixin from 'react-addons-linked-state-mixin';
import $ from 'jquery';
import NavMain from '../components/navigation/NavMain';
import PageFooter from '../components/PageFooter';
import PageHeader from '../components/PageHeader';
import { jobURI, jobDetailURI } from '../utils/Config'
import ResourceSelector  from '../components/resources/ResourceSelector';

const JobDetails = React.createClass({
  mixins: [LinkedStateMixin],

  save(event){
    var jobConfig = this.state;
    jobConfig.resourceName = this.refs.resourceSelector.state.selected;
    this.put(jobConfig);
  },

  put(data) {
    var request = $.ajax({
      type: "PUT",
      url: jobURI(),
      contentType: 'application/json',
      data: JSON.stringify(data),
      dataType: "json"
    });

    request.done(function (msg) {
      //TODO use react-router transitionTo
      $(location).attr('href', '../jobs')
    });

    request.fail(function (jqXHR, msg) {
      console.log("Request failed: " + msg);
    });

  },

  loadData() {
    return $.getJSON(jobDetailURI(this.props.params.name));
  },

  getInitialState() {
    return {name: '', resourceName: '', executionTime: '0 0 0/1 1/1 * ? *', executionDirectory: '', enabled: false};
  },

  componentDidMount(){
    //If name is not null, it is deducted that is in edit mode
    //If is null, new job is to be added
    //TODO needs better way to decide whether it is edit or add
    if (this.props.params.name != null) {
      this.loadData().success(function (data) {
        if (this.isMounted()) {
          this.setState(data);
          this.refs.resourceSelector.setState({selected: this.state.resourceName});
        }
      }.bind(this))
    }
  },

  render() {
    return (
      <div>
        <NavMain activePage="jobs"/>
        <Grid>
          <Row>
            <div className="box box-success">
              <div className="box-header with-border">
                <h3 className="box-title">{this.props.params.name != null ? this.props.params.name : "New Job"}</h3>
              </div>

              <form role="form">
                <div className="box-body">

                  <div className="form-group">
                    <label>Name of the job</label>
                    <input type="text" className="form-control" valueLink={this.linkState('name')}
                           placeholder="Name of the job"></input>
                  </div>


                  <div className="form-group">
                    <div className="checkbox">
                      <label>
                        <input type="checkbox" checkedLink={this.linkState('enabled')}>
                          Enabled</input>
                      </label>
                    </div>
                  </div>


                  <div className="form-group">
                    <label>Execution Directory</label>
                    <input type="text" className="form-control" valueLink={this.linkState('executionDirectory')}
                           placeholder="Execution Directory"></input>
                  </div>


                  <div className="form-group">
                    <ResourceSelector ref="resourceSelector" selected={this.state.resourceName}/>
                  </div>


                  <div className="form-group">
                    <label>Execution Time</label>
                    <input type="text" className="form-control" valueLink={this.linkState('executionTime')}
                           placeholder="Execution Time"></input>
                  </div>

                  <div className="box-footer">
                    <button type="submit" className="btn btn-primary" onClick={this.save}>Save</button>
                  </div>

                </div>

              </form>

            </div>

          </Row>
        </Grid>
        <PageFooter />
      </div>
    );
  }
});

export default JobDetails;
