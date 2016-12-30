var Greetings = React.createClass({
  render: function() {
    return (
      <div>
        <p>Hello, {this.props.name || 'World'}</p>
      </div>
    );
  }
});

