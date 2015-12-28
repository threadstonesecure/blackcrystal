import React from 'react';

import NavMain from './../navigation/NavMain';
import PageHeader from './../PageHeader';
import PageFooter from './../PageFooter';
import { Input } from 'react-bootstrap';
import $ from 'jquery';


const JobDetails = React.createClass({
  loadData() {
      return $.getJSON("http://localhost:8080/job/"+this.props.params.name);
  },
  getInitialState() {
    return { data: [] };
  },

  componentDidMount(){
    this.loadData().success(function (data) {
        if(this.isMounted())
        {
            this.setState({data: data});
        }
    }.bind(this))     
  },

  render() {
    return (
      <div>
        <NavMain activePage="jobs" />

        <PageHeader title={this.props.params.name} />

        <div className="container bs-docs-container">
          <Input type="text" value={this.state.data.name} 
          placeholder="Name of the job"
          label="Name of the job"  />

           <Input type="checkbox" value={this.state.data.enabled} 
          label="Enabled"  />

          <Input type="text" value={this.state.data.executionDirectory} 
          placeholder="Execution Directory"
          label="Execution Directory"  />

          <Input type="text" value={this.state.data.sourcePath} 
          placeholder="Resource"
          label="Resource"  />

          <Input type="text" value={this.state.data.executionTime} 
          placeholder="Execution Time"
          label="Execution Time"  />

        </div>
        <PageFooter />
      </div>
    );
  }
});

export default JobDetails;
